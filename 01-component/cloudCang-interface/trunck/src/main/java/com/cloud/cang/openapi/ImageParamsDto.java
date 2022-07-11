package com.cloud.cang.openapi;

import lombok.Builder;
import lombok.Data;

/**
 * @program: cloudCang-vis
 * @description: ImageParamsDto
 * @author: qzg
 * @create: 2020-09-08 16:01
 **/
@Data
@Builder
public class ImageParamsDto {

    // 模型编号
    private String modelCode;
    // 图片编号
    private String imgCode;
    // 图片base64
    private String imgBase64;
    // 默认jpg
    private String imgFormat;
    // 图片保存全路径
    private String imgPath;
    // 10 不带坐标； 20 带坐标
    private String requestType;

}
