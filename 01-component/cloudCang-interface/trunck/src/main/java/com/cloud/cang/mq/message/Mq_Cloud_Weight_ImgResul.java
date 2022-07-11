package com.cloud.cang.mq.message;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Description: 云端称重识别信息
 * @Author: ylf
 * @Date: 2019/11/29 15:17
 */
@Data
@Builder
public class Mq_Cloud_Weight_ImgResul {
    private Integer type;//10 开门 20 实时 30 关门
    private String deviceCode;//设备编号
    private String deviceId;//设备Id
    private String userId;//用户Id
    private String shopLayeredGoods;//购物实时、关门商品信息  补货实时商品信息 ShopLayeredGoods
    private String goods;//补货关门商品信息 Goods
    private Integer openSource;////开门来源 10 人脸识别，20 扫码（支付宝，微信）
    private boolean success;//是否成功
    private Integer openDoorType;//开门类型10购物 20补货
    private String picUrlList;//开关门识别图片地址
    private String uniquelyIdentifies;//关门唯一标识


    @Data
    @NoArgsConstructor
    public static class Mq_Cloud_Img {
        private String imageUrl;
        private String cameraCode;
    }
}
