package com.cloud.cang.mq.message;

import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0
 * @Description: 云端称重识别异常信息
 * @Author: ylf
 * @Date: 2019/11/29 15:17
 */
@Data
@Builder
public class Mq_Cloud_Weight_Exception_ImgResul {
    private String userId;//用户Id
    private String deviceId;//设备Id
    private Integer methodType;//方法名称 10 开门 20 关门
}
