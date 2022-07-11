package com.cloud.cang.mq.message;

import lombok.Builder;
import lombok.Data;

/**
 * @program: 37cang-云平台
 * @description: 模型更新
 * @author: qzg
 * @create: 2019-11-21 16:28
 **/
@Data
@Builder
public class Mq_ModelUpdate {
    // ip地址
    private String host;
    // 端口
    private String port;
    // 模型编号
    private String modelCode;
}


