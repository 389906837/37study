package com.cloud.cang.core.rmq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-14 13:56
 **/
@Component
@Data
@Conditional(RmqCondition.class)
public class RmqProperties {
    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private Integer port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

}
