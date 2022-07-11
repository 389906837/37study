package com.cloud.cang.core.rmq.config;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-15 09:23
 **/
public class RmqCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            RmqEnable bean = context.getBeanFactory().getBean(RmqEnable.class);
            if(null != bean ){
                return bean.isEnable();
            }
            return false;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }
}
