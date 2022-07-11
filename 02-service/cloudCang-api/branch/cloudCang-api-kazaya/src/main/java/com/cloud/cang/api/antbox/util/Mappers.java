package com.cloud.cang.api.antbox.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.text.SimpleDateFormat;

public class Mappers {
	public static final String DEFAULT_DATE_FORMAT_TPL = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_FORMAT_TPL);

    /**
     * json 解释器
     */
    public static final ObjectMapper objectMapper;

    /**
     * xml 解析器
     */
    public static final XmlMapper xmlMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(DEFAULT_DATE_FORMAT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // 过滤对应类找不到字段
    }
    
}
