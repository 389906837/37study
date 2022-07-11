package com.cloud.cang.mq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @program: 37cang-云平台
 * @description: 图片识别消息
 * @author: qzg
 * @create: 2019-11-21 16:28
 **/
@Data
@Builder
public class Mq_ImgRec {
    private String batchNo;
    private String deviceId;
    private String userId;
    private Integer responseType;//第三方调用使用  返回结果类型 10=不带坐标 20=带坐标
    private List<ImgRecoItem> imgRecoItems;

    @Data
    @AllArgsConstructor
    public static class ImgRecoItem{
        private String cameraCode; //获取照片标识
        private String imageUrl;//图片地址 可访问
    }
}


