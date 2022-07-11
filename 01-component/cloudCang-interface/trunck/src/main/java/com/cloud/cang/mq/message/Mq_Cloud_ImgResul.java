package com.cloud.cang.mq.message;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Description: 云端识别信息
 * @Author: ylf
 * @Date: 2019/11/29 15:17
 */
@Data
@Builder
public class Mq_Cloud_ImgResul {
    private String deviceId; //设备ID
    private String key;//通信密钥
    private String userId;//用户ID
    private Integer openDoorType;//开门类型10购物 20补货
    private Integer type;//10 开门 20 实时 30 关门
    private String imgResultDetail;//商品集合 List<ImgResultDetail>
    private boolean success;//是否成功
    private String picUrlList;//开关门识别图片地址
    private String uniquelyIdentifies;//关门唯一标识

    @Data
    @NoArgsConstructor
    public static class Mq_Cloud_Img {
        private String imageUrl;
        private String cameraCode;
        private String closeImgUrl;
    }
}
