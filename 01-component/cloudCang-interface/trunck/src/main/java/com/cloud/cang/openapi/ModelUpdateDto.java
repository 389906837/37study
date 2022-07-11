package com.cloud.cang.openapi;

import lombok.Data;

/**
 * @program: 37cang-云平台
 * @description: 模型更新
 * @author: qzg
 * @create: 2019-11-28 16:58
 **/
@Data
public class ModelUpdateDto {
    // 识别服务ip
    private String host;
    // 识别服务端口
    private String port;
    // 新模型编号
    private String newModelCode;
    // 旧模型编号
    private String oldModelCode;
}
