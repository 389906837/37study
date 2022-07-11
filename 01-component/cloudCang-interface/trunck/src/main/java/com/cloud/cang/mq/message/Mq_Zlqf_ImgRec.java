package com.cloud.cang.mq.message;

import lombok.Builder;
import lombok.Data;

/**
 * @program: cloudCang-vis
 * @description: Mq_Zlqf_ImgRec
 * @author: qzg
 * @create: 2020-09-08 19:24
 **/
@Data
@Builder
public class Mq_Zlqf_ImgRec {
    private String imgCode;//图片编号
    private String imgBase64;// 图片base64
    private String imgFormat; //图片后缀
    private String imgPath; //图片路径
    private Integer responseType;//返回结果类型 10=不带坐标 20=带坐标
}
