package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.dto.BoxOpenedDto;
import com.cloud.cang.api.antbox.message.AntboxHeartbeat;
import com.cloud.cang.api.antbox.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 心跳监听器
 *  5分钟内没有关门，发送邮件警告
 */
@Component
public class AntboxHeartbeatListener implements MessageListener<AntboxHeartbeat> {

    private static final Logger log = LoggerFactory.getLogger(AntboxHeartbeatListener.class);
    private static final long MAX_OPENED_TIME = 5 * 60 * 1000L;

    @Override
    public void onMessage(BoxInfo boxInfo, AntboxHeartbeat msg) {
        if (boxInfo == null || boxInfo.getBoxId() == null) {
            return;
        }
        if (boxInfo.getStatus() == BoxStatus.OPENED) {
            checkOpenDoorTimeout(boxInfo);
        } else {
            boxInfo.setOpenDoorTime(null);
        }
    }

    /**
     * 开门状态下，如果5分钟还没有关门，发邮件通知
     */
    private void checkOpenDoorTimeout(BoxInfo boxInfo) {
        if (boxInfo.getOpenDoorTime() == null) {
            boxInfo.setOpenDoorTime(System.currentTimeMillis());
        } else {
            if ((System.currentTimeMillis() - boxInfo.getOpenDoorTime()) > MAX_OPENED_TIME) {
                try {
                    BoxOpenedDto dto = new BoxOpenedDto();
                    dto.setBoxId(boxInfo.getBoxId());
                    dto.setOpenTime(boxInfo.getOpenDoorTime());
                    String json = Utils.objectMapper.writeValueAsString(dto);
                    log.info("box open for too long time, send alarm: {}", json);
                    //TODO 放入缓存队列
                } catch (Exception e) {
                    log.error("box open for too long time, but send alarm failed, reason: {}", e.getMessage());
                }
            }
        }
    }
}
