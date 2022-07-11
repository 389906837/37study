package com.cloud.cang.mq.message;

import lombok.Data;

import java.util.List;

/**
 * @program: 37cang-云平台
 * @description: 调用底层识别结果
 * @author: qzg
 * @create: 2019-11-23 16:06
 **/
@Data
public class Mq_ImgResult {
    // 返回状态 200=成功
    private int status;

    private String batchNo;

    private String userId;

    private String deviceId;

    // 返回内容
    private List<ImgResultItem> msg;

    @Data
    public class ImgResultItem{
        private String cameraCode;
        private String status;
        private String imageUrl;
        private List<Goods> goods;
    }

    @Data
    public class Goods{
        private String svrCode;
        private int number;

        //带坐标
        private String prob;
        private String posx;
        private String posy;
        private String posw;
        private String posh;
    }

}







