package com.cloud.cang.mq.message;

import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0
 * @Description: 云端识别异常信息
 * @Author: ylf
 * @Date: 2019/11/29 15:17
 */
@Data
@Builder
public class Mq_Cloud_Exception_ImgResul {
    private String deviceId;//设备ID
    private String key;//通信密钥
    private String userId;//用户ID
    private Integer openDoorType;//开门类型 10购物 20补货
    private Integer methodType;//方法名称 10 开门 20 关门
    private String errorCode;//错误代码
}
