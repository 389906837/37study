package com.cloud.cang.message.xx.service.impl;

import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.message.sms.impl.MengWangMarketingSmsSender;
import com.cloud.cang.message.sms.IEmailSender;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.dao.MsgTaskDao;
import com.cloud.cang.message.xx.service.MsgTemplateMainService;
import com.cloud.cang.message.xx.service.SmsSendProxyService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsSendProxyServiceImpl implements SmsSendProxyService {

    private static Logger LOGGER = LoggerFactory.getLogger(SmsSendProxyServiceImpl.class);

    @Autowired
    @Qualifier("chuangMingSmsSender")
    ISmsSender chuangMingSmsSender;

    @Autowired
    @Qualifier("jianZhouSmsSender")
    ISmsSender jianZhouSmsSender;

    @Autowired
    @Qualifier("manDaoSmsSender")
    ISmsSender manDaoSmsSender;

    @Autowired
    @Qualifier("mengWangSmsSender")
    ISmsSender mengWangSmsSender;

    @Autowired
    @Qualifier("mengWangMarketingSmsSender")
    MengWangMarketingSmsSender mengWangMarketingSmsSender;

    @Autowired
    @Qualifier("sanTongSmsSender")
    ISmsSender sanTongSmsSender;

    @Autowired
    @Qualifier("sanTongMarketingSmsSender")
    ISmsSender sanTongMarketingSmsSender;

    @Autowired
    @Qualifier("xuanWuSmsSender")
    ISmsSender xuanWuSmsSender;

    @Autowired
    @Qualifier("xuanWuMarketingSmsSender")
    ISmsSender xuanWuMarketingSmsSender;

    @Autowired
    @Qualifier("twoFactorSmsSender")
    ISmsSender twoFactorSmsSender;

    @Autowired
    IEmailSender iEmailSender;

    @Autowired
    MsgTaskDao msgTaskDao;

    @Autowired
    ICached iCached;

    @Autowired
    MsgTemplateMainService msgTemplateMainService;


    @Override
    public void batchSendMobileMessage(final MsgTask mgsTask, final List<String> sendMobiles) {

        ExecutorManager.getInstance().execute(new Runnable() {
            String serNum = "";

            @Override
            public void run() {
                try {
                  /*  if (mgsTask.getSsupplierCode().equals(Constrants.SMS_CHUANGMING_CODE)) {
                        serNum = chuangMingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_JIANZHAOU_CODE)) {
                        serNum = jianZhouSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MANDAO_CODE)) {
                        serNum = manDaoSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_CODE)) {
                        serNum = mengWangSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
                        serNum = mengWangMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_CODE)) {
                        serNum = sanTongSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_MARKING_CODE)) {
                        serNum = sanTongMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(// 玄武短信code
                            Constrants.SMS_XUANWU_CODE)) {
                        serNum = xuanWuSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                        // 玄武营销短信
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_XUANWU_MARKING_CODE)) {
                        serNum = xuanWuMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
                    }*/
                    serNum = getMessage(mgsTask, sendMobiles);

                    mgsTask.setSserianNum(serNum);
                    if (StringUtils.isNotBlank(serNum) && serNum.indexOf("-99") > -1) {
                        mgsTask.setIstate(4);
                    } else {
                        mgsTask.setIstate(3);
                    }
                    mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                    msgTaskDao.updateByIdSelective(mgsTask);

                    MsgTemplate msgTemplate = msgTemplateMainService.getMsgTemplateById(mgsTask.getSsenderTemplateId());
                    if (msgTemplate != null && msgTemplate.getSendCountLimit() != null && msgTemplate.getSendCountLimit() != 0) {

                        String keyName = Constrants.INTERNAL_SMS + DateUtils.getCurrentDTimeNumber();
                        // 模板发送限制次数
                        Integer limit_count = null;
                        try {
                            limit_count = (Integer) iCached.hget(keyName, Constrants.MESSAGE_LIMIT_USER_PREFIX + mgsTask.getSmerchantCode() + msgTemplate.getId());
                            if (limit_count != null) {
                                limit_count = limit_count + 1;
                            } else {
                                limit_count = 1;
                            }
                            iCached.hset(keyName, Constrants.MESSAGE_LIMIT_USER_PREFIX + mgsTask.getSmerchantCode() + msgTemplate.getId(), limit_count);
                            // 一天后失效
                            iCached.expireSec(keyName, 24 * 60 * 60);
                        } catch (Exception e) {
                            LOGGER.error("", e);
                        }
                    }


                } catch (SMSException e) {
                    LOGGER.error("消息发送失败-->", e);
                    if (e.getMessage().equals("手机号格式不正确")) {
                        mgsTask.setIstate(4);
                        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                        msgTaskDao.updateByIdSelective(mgsTask);
                    }
                }

            }

        });

    }


    @Override
    public void sendMobileMessage(final MsgTask mgsTask) {

        ExecutorManager.getInstance().execute(new Runnable() {
            String serNum = "";

            @Override
            public void run() {
                try {
                  /*  if (mgsTask.getSsupplierCode().equals(Constrants.SMS_CHUANGMING_CODE)) {
                        serNum = chuangMingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_JIANZHAOU_CODE)) {
                        serNum = jianZhouSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MANDAO_CODE)) {
                        serNum = manDaoSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_CODE)) {
                        serNum = mengWangSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
                        serNum = mengWangMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_CODE)) {
                        serNum = sanTongSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_MARKING_CODE)) {
                        serNum = sanTongMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    } else if (mgsTask.getSsupplierCode().equals(// 玄武短信code
                            Constrants.SMS_XUANWU_CODE)) {
                        serNum = xuanWuSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                        // 玄武营销短信
                    } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_XUANWU_MARKING_CODE)) {
                        serNum = xuanWuMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                    }*/
                    serNum = getMessage(mgsTask);

                    mgsTask.setSserianNum(serNum);
                    if (StringUtils.isNotBlank(serNum) && serNum.indexOf("-99") > -1) {
                        mgsTask.setIstate(4);
                    } else {
                        mgsTask.setIstate(3);
                    }

                    mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                    msgTaskDao.updateByIdSelective(mgsTask);
                    MsgTemplate msgTemplate = msgTemplateMainService.getMsgTemplateById(mgsTask.getSsenderTemplateId());
                    if (msgTemplate != null && msgTemplate.getSendUserCountLimit() != null && msgTemplate.getSendUserCountLimit() != 0) {

                        String keyName = Constrants.MESSAGE_LIMIT_KEY + mgsTask.getSsenderTemplateId() + DateUtils.getCurrentDTimeNumber();
                        // 用户发送限制次数
                        Integer limit_count = null;
                        try {
                            limit_count = (Integer) iCached.hget(keyName, Constrants.MESSAGE_LIMIT_USER_PREFIX + mgsTask.getSmsgRecipient());
                            if (limit_count != null) {
                                limit_count = limit_count + 1;
                            } else {
                                limit_count = 1;
                            }
                            iCached.hset(keyName, Constrants.MESSAGE_LIMIT_USER_PREFIX + mgsTask.getSmsgRecipient(), limit_count);
                            // 一天后失效
                            iCached.expireSec(keyName, 24 * 60 * 60);
                        } catch (Exception e) {
                            LOGGER.error("", e);
                        }
                    }
                } catch (SMSException e) {
                    LOGGER.error("消息发送失败-->", e);
                    if (e.getMessage().equals("手机号格式不正确")) {
                        mgsTask.setIstate(4);
                        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                        msgTaskDao.updateByIdSelective(mgsTask);
                    }
                }

            }

        });

    }


    @Override
    public void sendEmailMessage(final MsgTask mgsTask) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String suc = iEmailSender.send(mgsTask.getSmsgRecipient(), mgsTask.getStitle(), mgsTask.getScontent(), mgsTask.getSattach());
                    if ("success".equals(suc)) {
                        mgsTask.setIstate(3);
                    } else {
                        mgsTask.setIstate(4);
                    }
                    mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                    msgTaskDao.updateByIdSelective(mgsTask);
                } catch (SMSException e) {
                    LOGGER.error("消息发送失败-->", e);
                }

            }

        });

    }

    @Override
    public String batchSendMobileMessageWithSynchro(MsgTask mgsTask, List<String> sendMobiles) {
        String serNum = "";
        try {
         /*   if (mgsTask.getSsupplierCode().equals(Constrants.SMS_CHUANGMING_CODE)) {
                serNum = chuangMingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_JIANZHAOU_CODE)) {
                serNum = jianZhouSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MANDAO_CODE)) {
                serNum = manDaoSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_CODE)) {
                serNum = mengWangSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
                serNum = mengWangMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_CODE)) {
                serNum = sanTongSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_MARKING_CODE)) {
                serNum = sanTongMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(// 玄武短信code
                    Constrants.SMS_XUANWU_CODE)) {
                serNum = xuanWuSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
                // 玄武营销短信
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_XUANWU_MARKING_CODE)) {
                serNum = xuanWuMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
            }*/
            serNum = getMessage(mgsTask, sendMobiles);

            mgsTask.setSserianNum(serNum);
            if (StringUtils.isNotBlank(serNum) && serNum.indexOf("-99") > -1) {
                mgsTask.setIstate(4);
            } else {
                mgsTask.setIstate(3);
            }
            mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
            msgTaskDao.updateByIdSelective(mgsTask);
        } catch (SMSException e) {
            LOGGER.error("消息发送失败-->", e);
            if (e.getMessage().equals("手机号格式不正确")) {
                mgsTask.setIstate(4);
                mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                msgTaskDao.updateByIdSelective(mgsTask);
            }
        }
        return serNum;

    }

    @Override
    public String sendMobileMessageWithSynchro(MsgTask mgsTask) {
        String serNum = "";
        try {
           /* if (mgsTask.getSsupplierCode().equals(Constrants.SMS_CHUANGMING_CODE)) {
                serNum = chuangMingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_JIANZHAOU_CODE)) {
                serNum = jianZhouSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MANDAO_CODE)) {
                serNum = manDaoSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_CODE)) {
                serNum = mengWangSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
                serNum = mengWangMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_CODE)) {
                serNum = sanTongSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_MARKING_CODE)) {
                serNum = sanTongMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            }*/
            serNum = getMessage(mgsTask);
            mgsTask.setSserianNum(serNum);
            if (StringUtils.isNotBlank(serNum) && serNum.indexOf("-99") > -1) {
                mgsTask.setIstate(4);
            } else {
                mgsTask.setIstate(3);
            }
            mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
            msgTaskDao.updateByIdSelective(mgsTask);
            MsgTemplate msgTemplate = msgTemplateMainService.getMsgTemplateById(mgsTask.getSsenderTemplateId());
            if (msgTemplate != null && msgTemplate.getSendUserCountLimit() != null && msgTemplate.getSendUserCountLimit() != 0) {

                String keyName = Constrants.MESSAGE_LIMIT_KEY + mgsTask.getSsenderTemplateId() + DateUtils.getCurrentDTimeNumber();
                // 用户发送限制次数
                Integer limit_count = null;
                try {
                    limit_count = (Integer) iCached.hget(keyName, Constrants.MESSAGE_LIMIT_USER_PREFIX + mgsTask.getSmsgRecipient());
                    if (limit_count != null) {
                        limit_count = limit_count + 1;
                    } else {
                        limit_count = 1;
                    }
                    iCached.hset(keyName, Constrants.MESSAGE_LIMIT_USER_PREFIX + mgsTask.getSmsgRecipient(), limit_count);
                    // 一天后失效
                    iCached.expireSec(keyName, 24 * 60 * 60);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        } catch (SMSException e) {
            LOGGER.error("消息发送失败-->", e);
            if (e.getMessage().equals("手机号格式不正确")) {
                mgsTask.setIstate(4);
                mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
                msgTaskDao.updateByIdSelective(mgsTask);
            }
        }

        return serNum;
    }

    @Override
    public String sendEmailMessageWithSynchro(MsgTask mgsTask) {
        String suc = "";
        try {
            suc = iEmailSender.send(mgsTask.getSmsgRecipient(), mgsTask.getStitle(), mgsTask.getScontent(), mgsTask.getSattach());
            if ("success".equals(suc)) {
                mgsTask.setIstate(3);
            } else {
                mgsTask.setIstate(4);
            }
            mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
            msgTaskDao.updateByIdSelective(mgsTask);
        } catch (SMSException e) {
            LOGGER.error("消息发送失败-->", e);
        }

        return suc;

    }

    @Override
    public String queryBalance(String supplierCode) {
        String balance = "";
        try {
            if (supplierCode.equals(Constrants.SMS_CHUANGMING_CODE)) {
                balance = chuangMingSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_JIANZHAOU_CODE)) {
                balance = jianZhouSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_MANDAO_CODE)) {
                balance = manDaoSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_MENGWANG_CODE)) {
                balance = mengWangSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
                balance = mengWangMarketingSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_SANTONG_CODE)) {
                balance = sanTongSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
                balance = sanTongMarketingSmsSender.queryRemainMessageCount();
            } else if (supplierCode.equals(Constrants.SMS_XUANWU_CODE)) {// 玄武短信code
                balance = xuanWuSmsSender.queryRemainMessageCount();
                // 玄武营销短信
            } else if (supplierCode.equals(Constrants.SMS_XUANWU_MARKING_CODE)) {
                balance =  xuanWuMarketingSmsSender.queryRemainMessageCount();
            }else if (supplierCode.equals(Constrants.SMS_TWO_FACTORY_CODE)) {
                balance =  twoFactorSmsSender.queryRemainMessageCount();
            }

        } catch (Exception e) {
            LOGGER.error("余额查询失败-->", e);
        }
        return balance;
    }

    private String getMessage(MsgTask mgsTask, List<String> sendMobiles) {
        if (mgsTask.getSsupplierCode().equals(Constrants.SMS_CHUANGMING_CODE)) {
            return chuangMingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_JIANZHAOU_CODE)) {
            return jianZhouSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MANDAO_CODE)) {
            return manDaoSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_CODE)) {
            return mengWangSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
            return mengWangMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_CODE)) {
            return sanTongSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_MARKING_CODE)) {
            return sanTongMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(// 玄武短信code
                Constrants.SMS_XUANWU_CODE)) {
            return xuanWuSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            // 玄武营销短信
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_XUANWU_MARKING_CODE)) {
            return xuanWuMarketingSmsSender.batchSendSameContent(sendMobiles, mgsTask.getScontent());
        }else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_TWO_FACTORY_CODE)) {
            return twoFactorSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        }

        return "";
    }


    private String getMessage(MsgTask mgsTask) {
        if (mgsTask.getSsupplierCode().equals(Constrants.SMS_CHUANGMING_CODE)) {
            return chuangMingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_JIANZHAOU_CODE)) {
            return jianZhouSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MANDAO_CODE)) {
            return manDaoSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_CODE)) {
            return mengWangSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_MENGWANG_MARKING_CODE)) {
            return mengWangMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_CODE)) {
            return sanTongSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_SANTONG_MARKING_CODE)) {
            return sanTongMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        } else if (mgsTask.getSsupplierCode().equals(// 玄武短信code
                Constrants.SMS_XUANWU_CODE)) {
            return xuanWuSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
            // 玄武营销短信
        } else if (mgsTask.getSsupplierCode().equals(Constrants.SMS_XUANWU_MARKING_CODE)) {
            return xuanWuMarketingSmsSender.send(mgsTask.getSmsgRecipient(), mgsTask.getScontent());
        }
        return "";
    }
}
