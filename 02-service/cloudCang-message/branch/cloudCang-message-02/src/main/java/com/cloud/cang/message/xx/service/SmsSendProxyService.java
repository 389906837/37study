package com.cloud.cang.message.xx.service;


import com.cloud.cang.model.xx.MsgTask;

import java.util.List;

public interface SmsSendProxyService {

    /**
     * 异步短信群发
     * @param mgsTask
     * @param sendMobiles
     */
    void batchSendMobileMessage(final MsgTask mgsTask, final List<String> sendMobiles);

    /**
     * 同步短信群发
     *
     * @param mgsTask
     * @param sendMobiles
     */
    String batchSendMobileMessageWithSynchro(final MsgTask mgsTask, final List<String> sendMobiles);

    /**
     * 异步短信单发
     *
     * @param mgsTask
     */
    void sendMobileMessage(final MsgTask mgsTask);

    /**
     * 同步短信单发
     *
     * @param mgsTask
     */
    String sendMobileMessageWithSynchro(final MsgTask mgsTask);

    /**
     * 异步发送邮件
     *
     * @param mgsTask
     */
    void sendEmailMessage(final MsgTask mgsTask);

    /**
     * 同步发送邮件
     *
     * @param mgsTask
     */
    String sendEmailMessageWithSynchro(final MsgTask mgsTask);

    /**
     * 根据供应商Code 查询短信余额
     *
     * @param supplierCode
     * @return
     */
    String queryBalance(String supplierCode);

}
