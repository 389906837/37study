package com.cloud.cang.wap.hy.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.message.ProtocolBuildDto;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.SiteResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 协议控制类
 *
 * @author zhouhong
 */
@Controller
@RequestMapping("/protocol")
public class ProtocolController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolController.class);

    /**
     * 注册协议
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/agreement/{type}")
    public String regAgreement(@PathVariable String type, ModelMap map) {
        ProtocolBuildDto protocolBuildDto = new ProtocolBuildDto();
        protocolBuildDto.setProtocol(true);
        ResponseVo<String> responseVo = null;
        if (StringUtils.isBlank(type)) {
            CodeEnum codeEnum = CodeEnum.ERROR_SYSTEMP;
            SiteResponseVo resVo = new SiteResponseVo(false, codeEnum.getCode(), codeEnum.getNameByCode(codeEnum.getCode()), -1);
            map.put("resVo", resVo);
            return "error/error";
        } else if ("reg".equals(type)) {
            protocolBuildDto.setTemplateId("43d28fae36b511e7aa1b000c297e5606");// 智能云仓注册协议
        } else if ("privacy".equals(type)) {
            protocolBuildDto.setTemplateId("6894207336b511e7aa1b000c297e5606");// 智能云仓隐私协议
        } else if ("trash".equals(type)) {
            protocolBuildDto.setTemplateId("e1a0a58209f411ea8169000c2937a246");// 智能垃圾桶授权协议
        } else {
            CodeEnum codeEnum = CodeEnum.ERROR_SYSTEMP;
            SiteResponseVo resVo = new SiteResponseVo(false, codeEnum.getCode(), codeEnum.getNameByCode(codeEnum.getCode()), -1);
            map.put("resVo", resVo);
            return "error/error";
        }

        String htmlXY = "";
        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.PROTOCOL_BULID_HTML_SERVICE);
            invoke.setRequestObj(protocolBuildDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                htmlXY = responseVo.getData();
            }
        } catch (Exception e) {
            logger.error("生成模板失败", e);
        }
        map.put("htmlXY", htmlXY);
        return "uc/agreement_page";
    }


}
