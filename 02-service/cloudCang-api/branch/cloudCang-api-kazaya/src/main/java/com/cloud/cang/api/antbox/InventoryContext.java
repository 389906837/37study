package com.cloud.cang.api.antbox;


import com.cloud.cang.api.antbox.message.CardMessage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class InventoryContext implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    InventoryContext() {
    }

    // 当前盘点轮次
    private short currentRound = Short.MIN_VALUE;

    /**
     * 多次盘点，总盘点次数 = 关门盘点1次 + 指令盘点(INVENTORY_COUNT-1)次 <br>
     * 盘点结果取并集
     */
    private int inventoryTimesCountdown = AntboxConfig.INVENTORY_COUNT;
    /**
     * 同一交易盘点次数
     */
    private int inventoryTimes = AntboxConfig.INVENTORY_COUNT;

    /**
     * 是否为测试盘点
     */
    private boolean isTesting;

    /**
     * 批量盘点的盘点间隔（毫秒）
     */
    private int roundInterval = 0;

    /**
     * 同一次盘点的轮次开始时间
     */
    private long inventoryRoundStartMills;

    /**
     * 本次盘点开始时间
     */
    private long inventoryStartMills;

    /**
     * 阻止下一轮盘点开始
     */
    private boolean stopNextRound;

    /**
     * 当前交易盘点结果标签集
     */
    private Set<String> currentInventoryLabels = new HashSet<String>();

    private Set<String> currentTestInventoryLabels = new HashSet<String>();

    /**
     * 上次交易盘点结果标签集
     */
    private Set<String> lastInventoryLabels = new HashSet<String>();

    private Set<String> lastTestInventoryLabels = new HashSet<String>();

    // 对比两次盘点结果 #############
    public Set<String> list1 = new HashSet<String>();
    public Set<String> list2 = new HashSet<String>();
    // 对比两次盘点结果 #############

    public Set<String> getCurrentInventoryLabels() {
        return this.isTesting ? currentTestInventoryLabels : currentInventoryLabels;
    }

    public Set<String> getLastInventoryLabels() {
        return this.isTesting ? lastTestInventoryLabels : lastInventoryLabels;
    }

    public boolean isTesting() {
        return isTesting;
    }

    void stopNextRound() {
        this.stopNextRound = false;
    }

    public boolean isStopNextRound() {
        return stopNextRound;
    }

    /**
     * 是否新一轮盘点
     */
    public boolean isNewInventoryRound(CardMessage cardMsg) {
        return currentRound != cardMsg.getRound();
    }

    /**
     * 是否新盘点
     */
    public boolean isNewInventory(CardMessage cardMsg) {
        return isNewInventoryRound(cardMsg)
                && inventoryTimes == inventoryTimesCountdown
                && CardMessage.SEQNO_START_VALUE == cardMsg.getSeqNo();
    }

    /**
     * 预设盘点参数
     *
     * @param isTesting      是否为测试盘点
     * @param inventoryTimes 盘点轮次
     * @param roundInterval  盘点间隔（毫秒）
     */
    public void presetInventoryConfig(boolean isTesting, int inventoryTimes, int roundInterval) {
        if (inventoryTimes <= 0) {
            return;
        }

        this.isTesting = isTesting;
        this.inventoryTimes = inventoryTimes;
        this.inventoryTimesCountdown = inventoryTimes;
        this.roundInterval = roundInterval;
    }

    /**
     * 标记此轮盘点开始
     *
     * @param currentRound 轮次
     */
    public void markInventoryRoundStart(short currentRound) {
        this.inventoryRoundStartMills = System.currentTimeMillis();
        this.currentRound = currentRound;
    }

    /**
     * 计算两此交易轮盘点后，丢失的标签
     *
     * @return
     */
    public Set<String> computeLossLabels() {
        Set<String> lossUidSet = new HashSet<String>();
        for (String uid : getLastInventoryLabels()) {
            if (getCurrentInventoryLabels().contains(uid))
                continue;

            lossUidSet.add(uid);
        }
        return lossUidSet;
    }

    /**
     * 计算两此交易轮盘点后，新增的标签
     *
     * @return
     */
    public Set<String> computeNewLabels() {
        Set<String> newUidSet = new HashSet<String>();
        for (String uid : getCurrentInventoryLabels()) {
            if (getLastInventoryLabels().contains(uid))
                continue;

            newUidSet.add(uid);
        }
        return newUidSet;
    }

    /**
     * 本次交易盘点结束
     */
    public void markInventoryFinished() {
        getLastInventoryLabels().clear();
        getLastInventoryLabels().addAll(currentInventoryLabels);
        getCurrentInventoryLabels().clear();

        this.isTesting = false;
        this.stopNextRound = false;
        this.roundInterval = 0;
        this.inventoryTimesCountdown = AntboxConfig.INVENTORY_COUNT;
        this.inventoryTimes = AntboxConfig.INVENTORY_COUNT;
    }

    public int inventoryTimesCountDownAndGet() {
        return --inventoryTimesCountdown;
    }

    public int getInventoryTimesCountdown() {
        return inventoryTimesCountdown;
    }

    public long getCurrentInventoryRoundStartMills() {
        return inventoryRoundStartMills;
    }

    public long getInventoryStartMills() {
        return inventoryStartMills;
    }

    public int getInventoryTimes() {
        return inventoryTimes;
    }

    /**
     * 标记盘点开始
     */
    public void markInventoryStart() {
        this.inventoryStartMills = System.currentTimeMillis();
    }

    public Set<String> getList1() {
        return list1;
    }

    public void setList1(Set<String> list1) {
        this.list1 = list1;
    }

    public Set<String> getList2() {
        return list2;
    }

    public void setList2(Set<String> list2) {
        this.list2 = list2;
    }

    /**
     * 在下一轮盘点前休眠一会
     */
    public void takeRestBeforeNextRound() {
        if (roundInterval > 0) {
            try {
                Thread.sleep(roundInterval);
            } catch (InterruptedException e) {

            }
        }
    }
}
