package com.cloud.cang.message.sms.impl;

import com.cloud.cang.message.exception.ProtocolException;
import com.cloud.cang.message.ProtocolBuildDto;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.message.sms.ProtocolFileService;
import com.cloud.cang.message.xx.service.MsgTemplateMainService;
import com.cloud.cang.utils.FreemarkUtils;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 生成协议服务
 * @author zhouhong
 * @version 1.0
 */
@Service
public class ProtocolFileServiceImpl implements ProtocolFileService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ProtocolFileServiceImpl.class);

	@Autowired
    MsgTemplateMainService msgTemplateMainService;

	@Override
	public String buildProtocol2HTML(ProtocolBuildDto protocolBuildDto)
			throws ProtocolException {
		String ProtocolHTML = "";
		// 获取Template
		MsgTemplate msgTemplate = msgTemplateMainService
				.getMsgTemplateById(protocolBuildDto.getTemplateId());
		if (msgTemplate == null) {
			LOGGER.error("生成协议失败，没有找到模板，模板ID:{}",protocolBuildDto.getTemplateId());
			throw new ProtocolException("没有找到模板");
		}
		String templateContent = msgTemplate.getStemplateContent();

		try {
			ProtocolHTML = FreemarkUtils.replaceParameters(
					protocolBuildDto.getValues(), templateContent);
		} catch (IOException | TemplateException e) {
			LOGGER.error("模板替换失败", e);
			throw new ProtocolException("模板替换失败");
		}
		return ProtocolHTML;
	}

}
