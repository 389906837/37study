package com.cloud.cang.core.rmq.config;

import cn.hutool.core.util.ArrayUtil;
import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.interfaces.RmqQueue;
import com.cloud.cang.core.rmq.listener.ConfirmCallBackListener;
import com.cloud.cang.core.rmq.listener.ReturnCallBackListener;
import com.cloud.cang.core.rmq.listener.RmqMessageListener;
import com.cloud.cang.zookeeper.confclient.secret.AESCryptUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableRabbit
@Conditional(RmqCondition.class)
public class RabbitmqConfig implements ApplicationContextAware {
    public static final String DEAD_QUEUE = "dead.queue";
    public static final String DEAD_EXCHANGE = "dead.exchange";
    ApplicationContext applicationContext;
    @Autowired
    RmqProperties rmqProperties;
    @Autowired
    Environment environment;

    @Bean
    @Conditional(RmqCondition.class)
    public ConnectionFactory connectionFactory(){

        CachingConnectionFactory factory = new CachingConnectionFactory(
                rmqProperties.getHost(),
                rmqProperties.getPort());
        factory.setUsername(rmqProperties.getUsername());
        // 密码解密
        factory.setPassword(AESCryptUtil.decryptByKey(rmqProperties.getPassword()));
        String activeProfile = environment.getActiveProfiles()[0];
        factory.setVirtualHost("/" + activeProfile);
        factory.setPublisherReturns(true);
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return factory;
    }

    @Bean
    @Conditional(RmqCondition.class)
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Conditional(RmqCondition.class)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);// true才会调用ReturnCallback.returnedMessage()方法
        rabbitTemplate.setConfirmCallback(this.applicationContext.getBean(ConfirmCallBackListener.class));
        rabbitTemplate.setReturnCallback(this.applicationContext.getBean(ReturnCallBackListener.class));
        return  rabbitTemplate;
    }

    @Bean
    @Conditional(RmqCondition.class)
    public ChannelAwareMessageListener channelAwareMessageListener(){
        return new RmqMessageListener(this.applicationContext);
    }

    /**
     * 死信队列Exchange
     * @return
     */
    @Bean
    @Conditional(RmqCondition.class)
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, true, false);
    }

    /**
     * 死信队列Queue
     * @return
     */
    @Bean
    @Conditional(RmqCondition.class)
    public Queue deadQueue(){
        Queue queue = new Queue(DEAD_QUEUE, true);
        return queue;
    }

    /**
     * 死信队列Exchange和Queue绑定
     * @return
     */
    @Bean
    @Conditional(RmqCondition.class)
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).withQueueName();
    }

    @Bean
    @Conditional(RmqCondition.class)
    public SimpleMessageListenerContainer messageListenerContainer() {
        RmqQueue rmqEnable = applicationContext.getBean(RmqQueue.class);
        Queue[] queues = ArrayUtil.toArray(rmqEnable.getQueues(),Queue.class);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        // 监听的队列
        container.setQueues(queues);
        // 开启手动 ACK
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置MessageListener
        container.setMessageListener(channelAwareMessageListener());

        RmqManager bean = applicationContext.getBean(RmqManager.class);
        bean.setSimpleMessageListenerContainer(container);
        return container;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext ;
    }
}
