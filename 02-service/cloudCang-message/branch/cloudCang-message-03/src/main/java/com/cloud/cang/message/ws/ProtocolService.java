package com.cloud.cang.message.ws;

import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.message.ProtocolBuildDto;
import com.cloud.cang.message.exception.ProtocolException;
import com.cloud.cang.message.sms.ProtocolFileService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 协议服务
 *
 * @author zhouhong
 * @version 1.0
 */
@RestController
@RequestMapping("/protocolService")
@RegisterRestResource
public class ProtocolService {

    @Autowired
    private ProtocolFileService protocolFileService;

    private static Logger LOGGER = LoggerFactory.getLogger(ProtocolService.class);

    //注册协议Id
    public static final String REGISTER_PROTOCOL_ID = "05b29db736b511e7aa1b000c297e5606";


    /**
     * 根据协议模板生成HTML协议
     *
     * @param protocolBuildDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/buildProtocol2HTML", method = RequestMethod.POST)
    public ResponseVo<String> buildProtocol2HTML(
            @RequestBody ProtocolBuildDto protocolBuildDto) {
        String protocolHtml = "";
        if (StringUtils.isBlank(protocolBuildDto.getTemplateId())) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("模板ID不能为空");
        }

        try {
            protocolHtml = protocolFileService.buildProtocol2HTML(protocolBuildDto);
        } catch (ProtocolException e) {
            LOGGER.error("生成协议服务失败", e);
            return ErrorCodeEnum.ERROR_PROTOCOL_BULID.getResponseVo(e.getMessage());
        }
        return ResponseVo.getSuccessResponse(protocolHtml);

    }


}
