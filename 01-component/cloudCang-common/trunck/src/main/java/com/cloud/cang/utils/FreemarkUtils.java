package com.cloud.cang.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemark工具类
 * @author zhouhong
 *
 */
public class FreemarkUtils {
	/**
	 * 数据处理模板（基于freemark）
	 * 
	 * @param params
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static String replaceParameters(Map<String, Object> params,
			String content) throws IOException, TemplateException {
		if (StringUtils.isBlank(content))
			return "";
		StringTemplateLoader loader = new StringTemplateLoader(); // 模版加载器
		loader.putTemplate("chain", content);
		Configuration config = new freemarker.template.Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		config.setTemplateLoader(loader);
		Template template = config.getTemplate("chain", "UTF-8");
		StringWriter out = new StringWriter();

		template.process(params, out);
		return out.toString();
	}

}
