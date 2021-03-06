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
            // ??????
            if (mgsTask.getImsgType() == Constrants.MOBILE_MESSAGE) {
                // ????????????
                if (mgsTask.getIisRealtime() == 1) {
                    if (mgsTask.getIstate() == 1) {
                        // ????????????????????????
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
            // ??????
            if (mgsTask.getImsgType() == Constrants.MOBILE_MESSAGE) {
                // ????????????
                if (mgsTask.getIisRealtime() == 1) {
                    if (mgsTask.getIstate() == 1) {
                        // ????????????????????????
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
     * ??????????????????
     *
     * @param messageDto
     * @param msgTemplate
     * @return
     */
    private MsgTask createTask(MessageDto messageDto, MsgTemplate msgTemplate) {
        // ??????Task??????
        MsgTask mgsTask = new MsgTask();
        //????????????
        mgsTask.setSmerchantCode(messageDto.getSmerchantCode());
        //??????ID
        mgsTask.setSmerchantId(messageDto.getSmerchantId());

        mgsTask.setIdelFlag(0);
        // 1.?????? 2.??????
        mgsTask.setImsgType(msgTemplate.getImsgType());
        // ??????
        mgsTask.setIsenderType(1);
        // ?????????
        if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE) {
            mgsTask.setSmsgRecipient(messageDto.getMobile());
        } else {
            mgsTask.setSmsgRecipient(messageDto.getEmail());
            mgsTask.setSattach(messageDto.getAttachLocation());
        }
        // ?????????????????????
        if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE && !ValidateUtils.isMobile(messageDto.getMobile())) {
            mgsTask.setIstate(4);
        } else {
            mgsTask.setIstate(1);
        }

        // ????????????
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
            // ????????????????????????
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
                // ????????????
                mgsTask.setIstate(5);
            } else {
                // ?????????
                mgsTask.setIstate(1);
            }
        }
        mgsTask.setStitle(msgTemplate.getStemplateTitle());
        // ?????????ID
        mgsTask.setSspartnerId(msgTemplate.getSsupplierId());
        // ???????????????
        mgsTask.setSsupplierCode(msgTemplate.getSsupplierCode());
        mgsTask.setSsenderTemplateId(msgTemplate.getId());

        mgsTask.setIusage(msgTemplate.getIusage());
        mgsTask.setItimeout(msgTemplate.getItimeout());
        mgsTask.setIisRealtime(msgTemplate.getIisRealtime());
        // ????????????
        mgsTask.setIisBatchSend(0);
        mgsTask.setTaddtime(DateUtils.getCurrentDateTime());
        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
        if (msgTemplate.getIisRealtime() == 1) {
            mgsTask.setTbeginSendDatetime(DateUtils.getCurrentDateTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            // ????????????
            String hour = sdf.format(DateUtils.getCurrentDateTime());
            // ??????????????????
            Integer endHour = Integer.parseInt(sdf.format(DateUtils.parseDateByFormat(msgTemplate.getSendtime(), "HH:mm:ss")));
            // ??????????????????
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

                    // ???????????????
                    int currentCount = 0;
                    StringBuilder sbStr = new StringBuilder(1500);
                    // ??????????????????
                    final List<String> sendMobiles = new ArrayList<String>(100);
                    // ????????????
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
                                // ??????Task
                                final MsgTask mgsTask = createBatchTask(
                                        messageDto, msgTemplate, mobileStr);
                                msgTaskDao.insert(mgsTask);
                                // ????????????
                                if (mgsTask.getIisRealtime() == 1) {
                                    if (mgsTask.getIstate() == 1) {
                                        // ????????????????????????
                                        if (enviornConfig.isProduct()) {
                                            smsSendProxyService
                                                    .batchSendMobileMessage(
                                                            mgsTask, sendMobiles);
                                        }
                                    }

                                }

                            }

                            sbStr.setLength(0);
                            // ???????????????
                            sendMobiles.clear();
                            sbStr = new StringBuilder(1500);
                        } else {
                            sbStr.append(mobiles.get(totalCount - 1) + ",");
                            sendMobiles.add(mobiles.get(totalCount - 1));
                        }

                    }
                }
            } else if (msgTemplate.getImsgType() == Constrants.EMAIL_MESSAGE) {
                // ???????????????

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
     * ????????????????????????
     *
     * @param messageDto
     * @param msgTemplate
     * @param mobileStr
     * @return
     */
    private MsgTask createBatchTask(MessageDto messageDto,
                                    MsgTemplate msgTemplate, String mobileStr) {
        // ??????Task??????
        MsgTask mgsTask = new MsgTask();
        //??????ID
        mgsTask.setSmerchantId(messageDto.getSmerchantId());
        //??????Code
        mgsTask.setSmerchantCode(messageDto.getSmerchantCode());
        mgsTask.setIdelFlag(0);
        // 1.?????? 2.??????
        mgsTask.setImsgType(msgTemplate.getImsgType());
        // ??????
        mgsTask.setIsenderType(2);
        // ?????????
        if (msgTemplate.getImsgType() == Constrants.MOBILE_MESSAGE) {
            mgsTask.setSmsgRecipient(mobileStr);
        }
        // ?????????????????????
        if (msgTemplate.getSendCountLimit() != null
                && msgTemplate.getSendCountLimit() != 0) {
            // ????????????????????????
            Integer limit_count = null;
            try {
                limit_count = (Integer) iCached.hget(Constrants.INTERNAL_SMS + DateUtils.getCurrentDTimeNumber(),
                        Constrants.MESSAGE_LIMIT_USER_PREFIX + messageDto.getSmerchantCode() + msgTemplate.getId());

            } catch (Exception e) {
                LOGGER.error("", e);
            }
            if (limit_count != null
                    && limit_count >= msgTemplate.getSendCountLimit()) {
                // ????????????
                mgsTask.setIstate(5);
            } else {
                // ?????????
                mgsTask.setIstate(1);
            }
        } else {
            // ?????????
            mgsTask.setIstate(1);
        }


        // ????????????
        String content = "";
        try {
            content = FreemarkUtils.replaceParameters(
                    messageDto.getContentParam(),
                    msgTemplate.getStemplateContent());
        } catch (IOException | TemplateException e) {
            LOGGER.error("", e);
        }
        mgsTask.setScontent(content);
        // ?????????ID
        mgsTask.setSspartnerId(msgTemplate.getSsupplierId());
        // ???????????????
        mgsTask.setSsupplierCode(msgTemplate.getSsupplierCode());
        mgsTask.setSsenderTemplateId(msgTemplate.getId());

        mgsTask.setIusage(msgTemplate.getIusage());
        mgsTask.setItimeout(msgTemplate.getItimeout());
        mgsTask.setIisRealtime(msgTemplate.getIisRealtime());
        // ????????????
        mgsTask.setIisBatchSend(1);
        mgsTask.setTaddtime(DateUtils.getCurrentDateTime());
        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
        if (msgTemplate.getIisRealtime() == 1) {
            mgsTask.setTbeginSendDatetime(DateUtils.getCurrentDateTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            // ????????????
            String hour = sdf.format(DateUtils.getCurrentDateTime());
            // ??????????????????
            Integer endHour = Integer.parseInt(sdf.format(DateUtils.parseDateByFormat(msgTemplate.getSendtime(), "HH:mm:ss")));
            // ??????????????????
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
     * ????????????????????????
     *
     * @param salesMsgDto
     * @param mobileStr
     * @return
     */
    private MsgTask createSalesBatchTask(SalesMsgDto salesMsgDto, String mobileStr) {
        MsgTemplate msgTemplate = salesMsgDto.getMsgTemplate();
        // ??????Task??????
        MsgTask mgsTask = new MsgTask();
        mgsTask.setSmerchantId(salesMsgDto.getMerchantId());
        mgsTask.setSmerchantCode(salesMsgDto.getMerchantCode());
        mgsTask.setIdelFlag(0);
        // 1.?????? 2.??????
        mgsTask.setImsgType(msgTemplate.getImsgType());
        // ??????
        mgsTask.setIsenderType(2);
        // ?????????
        mgsTask.setSmsgRecipient(mobileStr);
        // ?????????????????????
        // ?????????
        mgsTask.setIstate(1);
        // ????????????
        String content = salesMsgDto.getContentParam();
        mgsTask.setScontent(content);
        // ?????????ID
        mgsTask.setSspartnerId(msgTemplate.getSsupplierId());
        // ???????????????
        mgsTask.setSsupplierCode(msgTemplate.getSsupplierCode());
       /* mgsTask.setSsenderTemplateId(msgTemplate.getId());*/
        mgsTask.setIusage(msgTemplate.getIusage());
        mgsTask.setItimeout(msgTemplate.getItimeout());
        mgsTask.setIisRealtime(msgTemplate.getIisRealtime());
        // ????????????
        mgsTask.setIisBatchSend(1);
        mgsTask.setTaddtime(DateUtils.getCurrentDateTime());
        mgsTask.setTupdatetime(DateUtils.getCurrentDateTime());
        mgsTask.setTbeginSendDatetime(DateUtils.getCurrentDateTime());
        return mgsTask;
    }

    @Override
    public String execInnerMessageTask(TemplateMain templateMain,
                                       InnerMessageDto innerMessageDto) {
        //??????:??????????????????????????????
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

            // ???????????????
            int currentCount = 0;
            StringBuilder sbStr = new StringBuilder(1500);
            // ??????????????????
            final List<String> sendMobiles = new ArrayList<String>(100);
            // ????????????
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
                        // ??????Task
                        final MsgTask mgsTask = createSalesBatchTask(
                                salesMsgDto, mobileStr);
                        msgTaskDao.insert(mgsTask);
                        // ????????????
                        if (mgsTask.getIisRealtime() == 1) {
                            if (mgsTask.getIstate() == 1) {
                                // ????????????????????????
                                if (enviornConfig.isProduct()) {
                                    smsSendProxyService
                                            .batchSendMobileMessage(
                                                    mgsTask, sendMobiles);
                                }
                            }

                        }

                    }

                    sbStr.setLength(0);
                    // ???????????????
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