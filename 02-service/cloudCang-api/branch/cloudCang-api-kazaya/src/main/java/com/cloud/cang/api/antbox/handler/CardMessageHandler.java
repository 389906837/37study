package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.InventoryContext;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.constant.InventoryType;
import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.CardMessage;
import com.cloud.cang.api.antbox.message.InventoryConclusion;
import com.cloud.cang.api.sb.service.DeviceSnapshotService;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Set;

@Component
public class CardMessageHandler extends BoxMessageHandler<CardMessage, InventoryConclusion> {

    @Autowired
    private DeviceSnapshotService deviceSnapshotService;

    @Override
    public void handle(BoxContext ctx, CardMessage cardMsg) throws IOException {
        if (ctx.getBoxInfo().getStatus() == BoxStatus.IDLE) {
            ctx.logError("current box status idle , ignoreCardMessage");
            return;
        }
        ctx.setStatus(BoxStatus.BUSY);
        // 上一次交易用户
        CustomerDto lastTradeUser = ctx.getLastCustomerDto();
        InventoryContext inventoryContext = ctx.getInventoryContext();
        InventoryType inventoryType = InventoryType.getByCode(cardMsg.getType());
        ctx.logInfo(
                "Inventory[{}]{} round[{}] report, seqNo: {} eventNo: {} labelCount: {} hasMore: {} labels[{}], lastTradeuser[{}] closeDoorAt: {}",
                inventoryType.name(), (inventoryContext.isTesting() ? " Testing" : ""),
                cardMsg.getRound(), cardMsg.getSeqNo(), cardMsg.getEventNo(), cardMsg.getUidNum(),
                cardMsg.hasNext(), Joiner.on(",").join(cardMsg.getUidSet()),
                ctx.getUserDesc(lastTradeUser), getUserCloseDoorTime(lastTradeUser));

        // 盘点发生：关门触发盘点、发送指令盘点触发一次盘点，一次盘点可以设置多轮
        // 是否为新的盘点，设置这次盘点的开始时间
        if (inventoryContext.isNewInventory(cardMsg)) {
            inventoryContext.markInventoryStart();
        }

        // 是否为新一轮的盘点
        if (inventoryContext.isNewInventoryRound(cardMsg)) {
            // 标记此轮盘点开始，设置此轮开始时间和第几轮
            inventoryContext.markInventoryRoundStart(cardMsg.getRound());
        }

        // 如果盘点到RFID数量大于0
        if (cardMsg.getUidNum() > 0) {
            // 把盘点到的RFID放入Set集合(去重)
            inventoryContext.getCurrentInventoryLabels().addAll(cardMsg.getUidSet());
            // 对比最后两轮盘点结果(把最后两次盘点的结果分别放入Set集合)
            if (inventoryContext.getInventoryTimesCountdown() == 2) {
                inventoryContext.getList1().addAll(cardMsg.getUidSet());
            } else if (inventoryContext.getInventoryTimesCountdown() == 1) {
                inventoryContext.getList2().addAll(cardMsg.getUidSet());
            }
        }

        // 如果是本轮盘点的最后一条消息
        if (!cardMsg.hasNext()) {
            conclusion(ctx, inventoryType, cardMsg.getRound());
        }
    }

    private void conclusion(BoxContext ctx, InventoryType type, int round) throws IOException {
        InventoryContext inventoryContext = ctx.getInventoryContext();
        // 此轮盘点耗时
        long elapsedMills = System.currentTimeMillis() - inventoryContext.getCurrentInventoryRoundStartMills();
        // 此轮盘点RFID标签数量
        int labelAmount = inventoryContext.getCurrentInventoryLabels().size();
        Set<String> lossLabels = inventoryContext.computeLossLabels();
        Set<String> newLabels = inventoryContext.computeNewLabels();
        String lossLabelsStr = Joiner.on(",").join(lossLabels);
        String newLabelsStr = Joiner.on(",").join(newLabels);

        ctx.logInfo("Inventory[{}]{} round[{}] summary, timesCountDown: {} labelAmount: {} lossLabels[{}]: {} newLabels[{}]: {} elapsedMills: {}",
                type.name(), (inventoryContext.isTesting() ? " Testing" : ""), round,
                inventoryContext.getInventoryTimesCountdown(), labelAmount, lossLabels.size(),
                lossLabelsStr, newLabels.size(), newLabelsStr, elapsedMills);

        /** 若此次盘点结束 **/
        if (inventoryContext.inventoryTimesCountDownAndGet() <= 0 || inventoryContext.isStopNextRound()) {
            // 此次盘点耗时
            elapsedMills = System.currentTimeMillis() - inventoryContext.getInventoryStartMills();
            // 实际盘点次数
            int actualExecTimes = inventoryContext.getInventoryTimes() - inventoryContext.getInventoryTimesCountdown();
            // 盘点结束
            ctx.logInfo("Inventory[{}]{} conclusion, inventoryTimes[expected: {} actual: {}], lossLabels[{}]: {} newLabels[{}]: {} elapsedMills: {}",
                    type.name(), (inventoryContext.isTesting() ? " Testing" : ""),
                    inventoryContext.getInventoryTimes(), actualExecTimes, lossLabels.size(),
                    lossLabels, newLabels.size(), newLabels, elapsedMills);

            // 设置用户盘点结束时间
            CustomerDto currentCustomerDto = ctx.getCurrentCustomerDto();
            if (currentCustomerDto != null) {
                currentCustomerDto.markInventoried();
            }

            // 更新售货机快照
            deviceSnapshotService.updateLastInventoryInfo(ctx.getBoxInfo().getBoxId(), inventoryContext.getCurrentInventoryLabels(), currentCustomerDto);

            //通知监听器盘点结果
            if (super.getListenerSize() > 0) {
                InventoryConclusion conclusion = new InventoryConclusion();
                conclusion.setBoxContext(ctx);
                conclusion.setActualInventoryTimes(actualExecTimes);
                conclusion.setElapsedMills(elapsedMills);
                conclusion.setExpectedInventoryTimes(inventoryContext.getInventoryTimes());
                conclusion.setCurrentInventoryLabels(inventoryContext.getCurrentInventoryLabels());
                conclusion.setLossLabels(lossLabels);
                conclusion.setNewLabels(newLabels);
                conclusion.setCurrentCustomer(currentCustomerDto);
                conclusion.setLastCustomer(ctx.getLastCustomerDto());
                conclusion.setTesting(inventoryContext.isTesting());
                conclusion.setInventoryType(type);
                super.notifyListeners(ctx, conclusion);
            }

            // 标记本次盘点结束
            inventoryContext.markInventoryFinished();

            // 交易完成，退出登录
            if (currentCustomerDto != null) {
                ctx.logout();
            }
            // 设置售货机状态：空闲
            ctx.setStatus(BoxStatus.IDLE);
            // 对比两次盘点结果
            if (!inventoryContext.getList2().equals(inventoryContext.getList1()))
                ctx.logInfo("diff result, list1.size : {},list2.size : {}",
                        inventoryContext.getList1().size(), inventoryContext.getList2().size());
            // 清空最后两次盘点结果
            inventoryContext.getList1().clear();
            inventoryContext.getList2().clear();
        }
        /** 若此次盘点未结束，还有下一轮 **/
        else {
            // 在下一轮盘点前休眠一会
            inventoryContext.takeRestBeforeNextRound();
            // 再次发送盘点指令
            try {
                ctx.getCommandSender().inventory();
            } catch (SendCommandException e) {
                ctx.logError(e, "doLastCardMsg orderSend next round inventory command fail.");
            }
        }
    }

    private String getUserCloseDoorTime(CustomerDto user) {
        if (user == null || user.getCloseDoorTime() == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCloseDoorTime());
    }

}
