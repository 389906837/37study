package com.cloud.cang.message.ws;


import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.sh.service.MerchantInfoService;
import com.cloud.cang.message.xx.domain.TemplateMain;
import com.cloud.cang.message.xx.service.MsgTaskService;
import com.cloud.cang.message.xx.service.MsgTemplateMainService;
import com.cloud.cang.message.xx.service.SmsSendProxyService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.MsgTemplateMain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/**
 * 消息服务
 *
 * @author zhouhong
 * @version 1.0
 */
@RestController
@RequestMapping("/smsService")
@RegisterRestResource
public class SMSService {

    @Autowired
    private MsgTemplateMainService msgTemplateMainService;

    @Autowired
    private MsgTaskService msgTaskService;

    @Autowired
    private SmsSendProxyService smsSendProxyService;
    @Autowired
    private MerchantInfoService merchantInfoService;


    /**
     * 发送短信
     *
     * @param messageDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseVo<String> sendMessage(@RequestBody MessageDto messageDto) {
        if (StringUtils.isBlank(messageDto.getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户编号为空");
        }
        if (StringUtils.isBlank(messageDto.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户ID为空");
        }
        if (StringUtils.isBlank(messageDto.getTemplateType())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("消息模板类型为空");
        }
        if (StringUtils.isBlank(messageDto.getMobile()) && StringUtils.isBlank(messageDto.getEmail())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("消息接收方不能为空");
        }
        //获取模板
        TemplateMain templateMain = getTemplate(messageDto.getSmerchantId(), messageDto.getTemplateType());
        if (templateMain == null) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("没有找到该消息模板");
        }
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        if (msgTemplates == null) {
            return ErrorCodeEnum.ERROR_MGC_TEMPLATE.getResponseVo();
        }
        //发送消息
        String sucMessage = msgTaskService.execMessageTask(templateMain, messageDto);

        if (!sucMessage.equalsIgnoreCase("success")) {
            return ErrorCodeEnum.ERROR_MGC_SEND.getResponseVo(sucMessage);
        }

        return ResponseVo.getSuccessResponse(sucMessage);
    }

    /**
     * 发送安全短信
     *
     * @param messageDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sendSecMessage", method = RequestMethod.POST)
    public ResponseVo<String> sendSecMessage(@RequestBody MessageDto messageDto) {
        if (StringUtils.isBlank(messageDto.getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户编号为空");
        }
        if (StringUtils.isBlank(messageDto.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户ID为空");
        }
        if (StringUtils.isBlank(messageDto.getTemplateType())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("消息模板类型为空");
        }
        if (StringUtils.isBlank(messageDto.getMobile()) && StringUtils.isBlank(messageDto.getEmail())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("消息接收方不能为空");
        }
        //获取模板
        TemplateMain templateMain = getTemplate(messageDto.getSmerchantId(), messageDto.getTemplateType());
        if (templateMain == null) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("没有找到该消息模板");
        }
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        if (msgTemplates == null) {
            return ErrorCodeEnum.ERROR_MGC_TEMPLATE.getResponseVo();
        }
        //发送消息

        String sucMessage = msgTaskService.execSecMessageTask(templateMain, messageDto);

        if (!sucMessage.equalsIgnoreCase("success")) {
            return ErrorCodeEnum.ERROR_MGC_SEND.getResponseVo(sucMessage);
        }

        return ResponseVo.getSuccessResponse(sucMessage);
    }

    /**
     * 批量发送消息
     *
     * @param messageDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/batchSendMessage", method = RequestMethod.POST)
    public ResponseVo<String> batchSendMessage(@RequestBody MessageDto messageDto) {
        if (StringUtils.isBlank(messageDto.getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户编号为空");
        }
        if (StringUtils.isBlank(messageDto.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户ID为空");
        }

        if (StringUtils.isBlank(messageDto.getTemplateType())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("消息模板类型为空");
        }
        if ((messageDto.getMobiles() == null || messageDto
                .getMobiles().isEmpty())
                && (messageDto.getEmails() == null || messageDto
                .getEmails().isEmpty())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("批量消息接收方不能为空");
        }
        //获取模板

        TemplateMain templateMain = getTemplate(messageDto.getSmerchantId(), messageDto.getTemplateType());

        if (templateMain == null) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("没有找到该消息模板");
        }
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        if (msgTemplates == null) {
            return ErrorCodeEnum.ERROR_MGC_TEMPLATE.getResponseVo();
        }
        //发送消息
        String sucMessage = msgTaskService.execBatchMessageTask(templateMain, messageDto);
        if (!sucMessage.equalsIgnoreCase("success")) {
            return ErrorCodeEnum.ERROR_MGC_SEND.getResponseVo(sucMessage);
        }

        return ResponseVo.getSuccessResponse(sucMessage);
    }

    /**
     * 发送消息给内部人，根据权限
     *
     * @param innerMessageDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sendMessageToInner", method = RequestMethod.POST)
    public ResponseVo<String> sendMessageToInner(
            @RequestBody InnerMessageDto innerMessageDto) {

        if (StringUtils.isBlank(innerMessageDto.getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户编号为空");
        }
        if (StringUtils.isBlank(innerMessageDto.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("商户ID为空");
        }

        if (StringUtils.isBlank(innerMessageDto.getTemplateType())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("消息模板类型为空");
        }
        if (StringUtils.isBlank(innerMessageDto.getPurviewCode())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("权限编号不能为空");
        }
        // 获取模板
        TemplateMain templateMain = getTemplate(innerMessageDto.getSmerchantId(), innerMessageDto.getTemplateType());
        if (templateMain == null) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("没有找到该消息模板");
        }
        Set<MsgTemplate> msgTemplates = templateMain.getMessageTemlates();
        if (msgTemplates == null) {
            return ErrorCodeEnum.ERROR_MGC_TEMPLATE.getResponseVo();
        }
        // 发送消息
        String sucMessage = msgTaskService.execInnerMessageTask(templateMain,
                innerMessageDto);

        if (!sucMessage.equalsIgnoreCase("success")) {
            return ErrorCodeEnum.ERROR_MGC_SEND.getResponseVo(sucMessage);
        }

        return ResponseVo.getSuccessResponse(sucMessage);
    }

    /**
     * 查询短信余额
     *
     * @param supplierCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/queryBalance/{supplierCode}", method = RequestMethod.GET)
    public ResponseVo<String> queryBalance(@PathVariable("supplierCode") String supplierCode) {
        if (StringUtils.isBlank(supplierCode)) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("供应商编号不能为空");
        }
        String balance = smsSendProxyService.queryBalance(supplierCode);
        return ResponseVo.getSuccessResponse(balance);

    }

    /**
     * 短信发送查询模板
     *
     * @param merchantId   商户ID
     * @param templateType 模板类型
     * @return TemplateMain  模板
     */
    private TemplateMain getTemplate(String merchantId, String templateType) {
        TemplateMain templateMain = msgTemplateMainService.getTemplateByMerchantId(merchantId, templateType);
        if (null == templateMain) {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            if ("0".equals(merchantInfo.getSrootId())) {
                return null;
            } else {
                return getTemplate(merchantInfo.getSrootId(), templateType);
            }
        }
        return templateMain;
    }
}
