package com.cloud.cang.mq.message;

import lombok.Data;

import java.util.List;

/**
 * @program: cloudCang-vis
 * @description: Mq_Zlqf_ImgResult
 * @author: qzg
 * @create: 2020-09-08 19:26
 **/
@Data
public class Mq_Zlqf_ImgResult {
    // 返回状态 200=成功
    private int status;

    private boolean success;//是否成功

    private List<Goods> goods;

    private String imgCode;

    private String imageUrl;

    @Data
    public class Goods {
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
