package com.cloud.cang.message.xx.service.impl;

import com.cloud.cang.message.SalesMsgDto;
import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.message.common.EnviornConfig;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.message.xx.dao.MsgTaskExtDao;
import com.cloud.cang.message.xx.service.MsgTaskService;
import com.cloud.cang.message.xx.service.SmsSendProxyService;
import com.cloud.cang.message.xx.dao.MsgTaskDao;
import com.cloud.cang.message.xx.domain.SysUseInfo;
import com.cloud.cang.message.xx.domain.TemplateMain;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FreemarkUtils;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MsgTaskServiceImpl extends GenericServiceImpl<MsgTask, String> implements
        MsgTaskService {


    private static Logger LOGGER = LoggerFactory.getLogger(MsgTaskServiceImpl.class);

    @Autowired
    MsgTaskDao msgTaskDao;

    @Autowired
    ICached iCached;

    @Autowired
    EnviornConfig enviornConfig;

    @Autowired
    SmsSendProxyService smsSendProxyService;

    @Autowired
    MsgTaskExtDao msgTaskExtDao;

    @Override
    public GenericDao<MsgTask, String> getDao() {
        return msgTaskDao;
    }

    @Override
    public String execMessageTask(TemplateMain templateMain,
                                  MessageDto messageDto) {
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        for (MsgTemplate msgTemplate : msgTemplates) {
            final MsgTask mgsTask = createTask(messageDto, msgTemplate);
            if (StringUtils.isBlank(mgsTask.getSmsgRecipient())) {
                continue;
            }
            msgTaskDao.insert(mgsTask);
            // 短息
            if (mgsTask.getImsgType() == Constrants.MOBILE_MESSAGE) {
                // 实时发送
                if (mgsTask.getIisRealtime() == 1) {
                    if (mgsTask.getIstate() == 1) {
                        // 生产环境发送短信
                        if (enviornConfig.isProduct()) {
                            smsSendProxyService.sendMobileMessage(mgsTask);
                        }
                    }

                }
            }
        }

        return "success";
    }

    @Override
    public String execSecMessageTask(TemplateMain templateMain,
                                     MessageDto messageDto) {
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        for (MsgTemplate msgTemplate : msgTemplates) {
            final MsgTask mgsTask = createTask(messageDto, msgTemplate);
            if (StringUtils.isBlank(mgsTask.getSmsgRecipient())) {
                continue;
            }
            // 短息
            if (mgsTask.getImsgType() == Constrants.MOBILE_MESSAGE) {
                // 实时发送
                if (mgsTask.getIisRealtime() == 1) {
                    if (mgsTask.getIstate() == 1) {
                        // 生产环境发送短信
                        if (enviornConfig.isProduct()) {
                            smsSendProxyService.sendMobileMessage(mgsTask);
                        }
                    }

                }
            }
        }

        return "success";
    }

    /**
     * 单条消息任务
     *
     * @param messageDto
     * @param msgTemplate
     * @return
     */
    private MsgTask createTask(MessageDto messageDto, MsgTemplate msgTemplate) {
        // 生成Task任务
        MsgTask mgsTask = new MsgTask();
        //商户编号
        mgsTask.setSmerchantCode(messageDto.getSmerchantCode());
        //商户ID
        mgsTask.setSmerchantId(messageDto.getSmerchantId());

        mgsTask.setIdelFlag(0);
        // 1.短信 2.邮件
        mgsTask.setImsgType(msgTemplate.getImsgType());
        // 单发
        mgsTask.setIsenderType(1);
        // 接收人
        if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE) {
            mgsTask.setSmsgRecipient(messageDto.getMobile());
        } else {
            mgsTask.setSmsgRecipient(messageDto.getEmail());
            mgsTask.setSattach(messageDto.getAttachLocation());
        }
        // 消息状态待发送
        if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE && !ValidateUtils.isMobile(messageDto.getMobile())) {
            mgsTask.setIstate(4);
        } else {
            mgsTask.setIstate(1);
        }

        // 消息内容
        String content = "";
        try {
            content = FreemarkUtils.replaceParameters(
                    messageDto.getContentParam(),
                    msgTemplate.getStemplateContent());
        } catch (IOException | TemplateException e) {
            LOGGER.error("", e);
        }
        mgsTask.setScontent(content);
        if (msgTemplate.getSendUserCountLimit() != null
                && msgTemplate.getSendUserCountLimit() != 0) {
            // 用户发送限制次数
            Integer limit_count = null;
            try {
                limit_count = (Integer) iCached.hget(
                        Constrants.MESSAGE_LIMIT_KEY + msgTemplate.getId()
                                + DateUtils.getCurrentDTimeNumber(),
                        Constrants.MESSAGE_LIMIT_USER_PREFIX
                                + messageDto.getMobile());
            } catch (Exception e) {
                LOGGER.error("", e);
            }

            if (limit_count != null
                    && limit_count >= msgTemplate.getSendUserCountLimit()) {
                // 超额发送
                mgsTask.setIstate(5);
            } else {
                // 待发送
                mgsTask.setIstate(1);
            }
        }
        mgsTask.setStitle(msgTemplate.getStemplateTitle());
        // 供应商ID
        mgsTask.setSspartnerId(msgTemplate.getSsupplierId());
        // 供应商编号
        mgsTask.setSsupplierCode(msgTemplate.getSsupplierCode());
        mgsTask.setSsenderTemplateId(msgTemplate.getId());

        mgsTask.setIusage(msgTemplate.getIusage());
        mgsTask.setItimeout(msgTemplate.getItimeout());
        mgsTask.setIisRealtime(msgTemplate.getIisRealtime());
        // 是否群发
        mgsTask.setIisBatchSend(0);
        mgsTask.setTaddtime(DateUtils.getCurrentDateTime());
        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
        if (msgTemplate.getIisRealtime() == 1) {
            mgsTask.setTbeginSendDatetime(DateUtils.getCurrentDateTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            // 当前小时
            String hour = sdf.format(DateUtils.getCurrentDateTime());
            // 模板结束小时
            Integer endHour = Integer.parseInt(sdf.format(DateUtils.parseDateByFormat(msgTemplate.getSendtime(), "HH:mm:ss")));
            // 模板开始小时
            Integer startHour = Integer.parseInt(sdf.format(DateUtils.parseDateByFormat(msgTemplate.getSstarttime(), "HH:mm:ss")));
            if (Integer.parseInt(hour) >= endHour) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.setTime(DateUtils.addDays(DateUtils.getToday(), 1));
                myCalendar.set(Calendar.HOUR_OF_DAY, startHour);
                mgsTask.setTbeginSendDatetime(myCalendar.getTime());
            } else {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.setTime(DateUtils.getToday());
                myCalendar.set(Calendar.HOUR_OF_DAY, startHour);
                mgsTask.setTbeginSendDatetime(myCalendar.getTime());

            }
        }

        return mgsTask;
    }

    @Override
    public String execBatchMessageTask(TemplateMain templateMain,
                                       MessageDto messageDto) {
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        for (MsgTemplate msgTemplate : msgTemplates) {
            if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE) {
                if (messageDto.getMobiles() != null
                        && messageDto.getMobiles().size() > 0) {
                    List<String> mobiles = messageDto.getMobiles();

                    // 当前记录数
                    int currentCount = 0;
                    StringBuilder sbStr = new StringBuilder(1500);
                    // 待发送的短信
                    final List<String> sendMobiles = new ArrayList<String>(100);
                    // 总记录数
                    int totalCount = 0;
                    for (int i = 0; i < mobiles.size(); i++) {
                        totalCount++;
                        if (StringUtils.isBlank(mobiles.get(totalCount - 1))) continue;
                        currentCount++;

                        if (currentCount == 100
                                || (currentCount < 100 && totalCount == mobiles
                                .size())) {
                            sbStr.append(mobiles.get(totalCount - 1) + ",");
                            if (sbStr != null && sbStr.toString().endsWith(",")) {
                                String mobileStr = sbStr.substring(0,
                                        sbStr.length() - 1);
                                // 创建Task
                                final MsgTask mgsTask = createBatchTask(
                                        messageDto, msgTemplate, mobileStr);
                                msgTaskDao.insert(mgsTask);
                                // 实时发送
                                if (mgsTask.getIisRealtime() == 1) {
                                    if (mgsTask.getIstate() == 1) {
                                        // 生产环境发送短信
                                        if (enviornConfig.isProduct()) {
                                            smsSendProxyService
                                                    .batchSendMobileMessage(
                                                            mgsTask, sendMobiles);
                                        }
                                    }

                                }

                            }

                            sbStr.setLength(0);
                            // 清空待发送
                            sendMobiles.clear();
                            sbStr = new StringBuilder(1500);
                        } else {
                            sbStr.append(mobiles.get(totalCount - 1) + ",");
                            sendMobiles.add(mobiles.get(totalCount - 1));
                        }

                    }
                }
            } else if (msgTemplate.getImsgType() == Constrants.EMAIL_MESSAGE) {
                // 待发送邮件

                if (messageDto.getEmails() != null
                        && messageDto.getEmails().size() > 0) {
                    List<String> emails = messageDto.getEmails();
                    for (int i = 0; i < emails.size(); i++) {
                        if (StringUtils.isBlank(emails.get(i))) continue;
                        messageDto.setEmail(emails.get(i));
                        MsgTask mgsTask = createTask(messageDto, msgTemplate);
                        msgTaskDao.insert(mgsTask);
                    }
                }

            }
        }
        return "success";
    }

    /**
     * 短信消息群发任务
     *
     * @param messageDto
     * @param msgTemplate
     * @param mobileStr
     * @return
     */
    private MsgTask createBatchTask(MessageDto messageDto,
                                    MsgTemplate msgTemplate, String mobileStr) {
        // 生成Task任务
        MsgTask mgsTask = new MsgTask();
        //商户ID
        mgsTask.setSmerchantId(messageDto.getSmerchantId());
        //商户Code
        mgsTask.setSmerchantCode(messageDto.getSmerchantCode());
        mgsTask.setIdelFlag(0);
        // 1.短信 2.邮件
        mgsTask.setImsgType(msgTemplate.getImsgType());
        // 群发
        mgsTask.setIsenderType(2);
        // 接收人
        if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE) {
            mgsTask.setSmsgRecipient(mobileStr);
        }
        // 消息状态待发送
        if (msgTemplate.getSendCountLimit() != null
                && msgTemplate.getSendCountLimit() != 0) {
            // 用户发送限制次数
            Integer limit_count = null;
            try {
                limit_count = (Integer) iCached.hget(Constrants.INTERNAL_SMS + DateUtils.getCurrentDTimeNumber(),
                        Constrants.MESSAGE_LIMIT_USER_PREFIX + messageDto.getSmerchantCode() + msgTemplate.getId());

            } catch (Exception e) {
                LOGGER.error("", e);
            }
            if (limit_count != null
                    && limit_count >= msgTemplate.getSendCountLimit()) {
                // 超额发送
                mgsTask.setIstate(5);
            } else {
                // 待发送
                mgsTask.setIstate(1);
            }
        } else {
            // 待发送
            mgsTask.setIstate(1);
        }


        // 消息内容
        String content = "";
        try {
            content = FreemarkUtils.replaceParameters(
                    messageDto.getContentParam(),
                    msgTemplate.getStemplateContent());
        } catch (IOException | TemplateException e) {
            LOGGER.error("", e);
        }
        mgsTask.setScontent(content);
        // 供应商ID
        mgsTask.setSspartnerId(msgTemplate.getSsupplierId());
        // 供应商编号
        mgsTask.setSsupplierCode(msgTemplate.getSsupplierCode());
        mgsTask.setSsenderTemplateId(msgTemplate.getId());

        mgsTask.setIusage(msgTemplate.getIusage());
        mgsTask.setItimeout(msgTemplate.getItimeout());
        mgsTask.setIisRealtime(msgTemplate.getIisRealtime());
        // 是否群发
        mgsTask.setIisBatchSend(1);
        mgsTask.setTaddtime(DateUtils.getCurrentDateTime());
        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
        if (msgTemplate.getIisRealtime() == 1) {
            mgsTask.setTbeginSendDatetime(DateUtils.getCurrentDateTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            // 当前小时
            String hour = sdf.format(DateUtils.getCurrentDateTime());
            // 模板结束小时
            Integer endHour = Integer.parseInt(sdf.format(DateUtils.parseDateByFormat(msgTemplate.getSendtime(), "HH:mm:ss")));
            // 模板开始小时
            Integer startHour = Integer.parseInt(sdf.format(DateUtils.parseDateByFormat(msgTemplate.getSstarttime(), "HH:mm:ss")));
            if (Integer.parseInt(hour) >= endHour) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.setTime(DateUtils.addDays(DateUtils.getToday(), 1));
                myCalendar.set(Calendar.HOUR_OF_DAY, startHour);
                mgsTask.setTbeginSendDatetime(myCalendar.getTime());
            } else {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.setTime(DateUtils.getToday());
                myCalendar.set(Calendar.HOUR_OF_DAY, startHour);
                mgsTask.setTbeginSendDatetime(myCalendar.getTime());

            }
        }

        return mgsTask;
    }

    /**
     * 短信消息群发任务
     *
     * @param salesMsgDto
     * @param mobileStr
     * @return
     */
    private MsgTask createSalesBatchTask(SalesMsgDto salesMsgDto, String mobileStr) {
        MsgTemplate msgTemplate = salesMsgDto.getMsgTemplate();
        // 生成Task任务
        MsgTask mgsTask = new MsgTask();
        mgsTask.setSmerchantId(salesMsgDto.getMerchantId());
        mgsTask.setSmerchantCode(salesMsgDto.getMerchantCode());
        mgsTask.setIdelFlag(0);
        // 1.短信 2.邮件
        mgsTask.setImsgType(msgTemplate.getImsgType());
        // 群发
        mgsTask.setIsenderType(2);
        // 接收人
        mgsTask.setSmsgRecipient(mobileStr);
        // 消息状态待发送
        // 待发送
        mgsTask.setIstate(1);
        // 消息内容
        String content = salesMsgDto.getContentParam();
        mgsTask.setScontent(content);
        // 供应商ID
        mgsTask.setSspartnerId(msgTemplate.getSsupplierId());
        // 供应商编号
        mgsTask.setSsupplierCode(msgTemplate.getSsupplierCode());
       /* mgsTask.setSsenderTemplateId(msgTemplate.getId());*/
        mgsTask.setIusage(msgTemplate.getIusage());
        mgsTask.setItimeout(msgTemplate.getItimeout());
        mgsTask.setIisRealtime(msgTemplate.getIisRealtime());
        // 是否群发
        mgsTask.setIisBatchSend(1);
        mgsTask.setTaddtime(DateUtils.getCurrentDateTime());
        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
        mgsTask.setTbeginSendDatetime(DateUtils.getCurrentDateTime());
        return mgsTask;
    }

    @Override
    public String execInnerMessageTask(TemplateMain templateMain,
                                       InnerMessageDto innerMessageDto) {
        //修改:用户需要有对应权限码
    /*	List<SysUseInfo> list = msgTaskExtDao
                .selectSysInfosByPurCode(innerMessageDto.getPurviewCode());*/
        Map<String, String> map = new HashMap<>();
        map.put("smerchantId", innerMessageDto.getSmerchantId());
        map.put("spurviewCode", innerMessageDto.getPurviewCode());

        List<SysUseInfo> list = msgTaskExtDao
                .selectSysInfosByPurCodeAndMerchantId(map);
        if (list == null || list.isEmpty())
            return "success";
        List<String> mobiles = new ArrayList<String>(list.size());
        List<String> emails = new ArrayList<String>(list.size());
        for (SysUseInfo sysUseInfo : list) {
            mobiles.add(sysUseInfo.getSmobile());
            emails.add(sysUseInfo.getEmail());
        }
        MessageDto messageTransfer = innerMessageDto;
        messageTransfer.setMobiles(mobiles);
        messageTransfer.setEmails(emails);
        return execBatchMessageTask(templateMain, messageTransfer);
    }

    @Override
    public String salesSendMessageTask(SalesMsgDto salesMsgDto) {
        if (salesMsgDto.getMobiles() != null
                && salesMsgDto.getMobiles().size() > 0) {
            List<String> mobiles = salesMsgDto.getMobiles();

            // 当前记录数
            int currentCount = 0;
            StringBuilder sbStr = new StringBuilder(1500);
            // 待发送的短信
            final List<String> sendMobiles = new ArrayList<String>(100);
            // 总记录数
            int totalCount = 0;
            for (int i = 0; i < mobiles.size(); i++) {
                totalCount++;
                if (StringUtils.isBlank(mobiles.get(totalCount - 1))) continue;
                currentCount++;

                if (currentCount == 100
                        || (currentCount < 100 && totalCount == mobiles
                        .size())) {
                    sbStr.append(mobiles.get(totalCount - 1) + ",");
                    if (sbStr != null && sbStr.toString().endsWith(",")) {
                        String mobileStr = sbStr.substring(0,
                                sbStr.length() - 1);
                        // 创建Task
                        final MsgTask mgsTask = createSalesBatchTask(
                                salesMsgDto, mobileStr);
                        msgTaskDao.insert(mgsTask);
                        // 实时发送
                        if (mgsTask.getIisRealtime() == 1) {
                            if (mgsTask.getIstate() == 1) {
                                // 生产环境发送短信
                                if (enviornConfig.isProduct()) {
                                    smsSendProxyService
                                            .batchSendMobileMessage(
                                                    mgsTask, sendMobiles);
                                }
                            }

                        }

                    }

                    sbStr.setLength(0);
                    // 清空待发送
                    sendMobiles.clear();
                    sbStr = new StringBuilder(1500);
                } else {
                    sbStr.append(mobiles.get(totalCount - 1) + ",");
                    sendMobiles.add(mobiles.get(totalCount - 1));
                }

            }
        }

        return "success";
    }


}