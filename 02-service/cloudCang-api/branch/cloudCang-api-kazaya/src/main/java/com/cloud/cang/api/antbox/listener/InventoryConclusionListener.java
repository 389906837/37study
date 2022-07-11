package com.cloud.cang.api.antbox.listener;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.message.InventoryConclusion;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.inventory.InventoryDto;
import com.cloud.cang.inventory.InventoryResult;
import com.cloud.cang.inventory.InventoryServicesDefine;
import com.corundumstudio.socketio.SocketIOClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

@Component
public class InventoryConclusionListener implements MessageListener<InventoryConclusion> {

    private static final Logger logger = LoggerFactory.getLogger(InventoryConclusionListener.class);
    private ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Override
    public void onMessage(BoxInfo boxInfo, InventoryConclusion msg) {
        if (boxInfo == null || boxInfo.getBoxId() == null
                || msg.getInventoryType().getCode() == 1)// 若是指令盘货暂时不作处理
            return;
        dealwithInventory(boxInfo, msg);
    }

    /**
     * 设备盘点处理服务
     */
    private void dealwithInventory (BoxInfo boxInfo, InventoryConclusion inventory){
        try{
            SocketResponseVo resVo = SocketResponseVo.getSuccessResponse();
            // 封装InventortDto
            InventoryDto inventoryDto = buildInventoryDto(boxInfo,inventory);
            // 调用盘点服务
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
                    .newInvoker(InventoryServicesDefine.INVENTORY_DEALWITH_SERVICE);
            invoke.setRequestObj(inventoryDto);
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {});
            ResponseVo<InventoryResult> responseVo = (ResponseVo<InventoryResult>) invoke.invoke();
            if (responseVo !=null && responseVo.isSuccess()) {
                //向客户端发送消息
                SocketIOClient client = socketIoMap.get(inventory.getCurrentCustomer().getDeviceId()+"_"+inventory.getCurrentCustomer().getId());
                resVo.setData(responseVo.getData());
                resVo.setMsg("close door success");
                Integer openDoorType = inventory.getCurrentCustomer().getDistribution() ? 20 : 10;
                if (openDoorType == 20) {
                    resVo.setTypes(40);//补货关门
                } else {
                    resVo.setTypes(30);//购物关门
                }
                client.sendEvent(NettyConst.EVENT_CLOSE_DOOR, JSON.toJSON(resVo));
            }else{
                // 关门盘货失败
                logger.error("调用关门盘货服务失败，boxId：{}", boxInfo.getBoxId());
                Integer openDoorType = inventory.getCurrentCustomer().getDistribution() ? 20 : 10;
                if (openDoorType == 20) {
                    resVo.setTypes(60);//补货异常
                    resVo.setErrorCode(ReturnCodeEnum.ERROR_REPLENISHMENT_EXCEPTION.getCode()); // 补货异常错误代码
                } else {
                    resVo.setTypes(50);//购物异常
                    resVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码
                }
                resVo.setMsg(responseVo.getMsg());
                resVo.setSuccess(false);

                //向客户端发送失败消息
                SocketIOClient client = socketIoMap.get(inventory.getCurrentCustomer().getDeviceId()+"_"+inventory.getCurrentCustomer().getId());
                resVo.setMsg("关门盘货异常");
                client.sendEvent(NettyConst.EVENT_CLOSE_DOOR, JSON.toJSON(resVo));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private InventoryDto buildInventoryDto(BoxInfo boxInfo, InventoryConclusion inventory){
        InventoryDto inventoryDto = new InventoryDto();
        // 关门类型 10 购物 20 补货员 (关门盘点时必填)
        if(inventory.getCurrentCustomer() !=null){
            inventoryDto.setDeviceId(inventory.getCurrentCustomer().getDeviceId());
            inventoryDto.setCloseType(inventory.getCurrentCustomer().getDistribution() ? 20 : 10);
            inventoryDto.setMemberId(inventory.getCurrentCustomer().getId());
        }
        inventoryDto.setInventoryType(10);
        inventoryDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.KAYAZA.getCode());
        inventoryDto.setLossLables(inventory.getLossLabels());
        inventoryDto.setNewLables(inventory.getNewLabels());
        inventoryDto.setCurrentLables(inventory.getCurrentInventoryLabels());
        return inventoryDto;
    }
}
