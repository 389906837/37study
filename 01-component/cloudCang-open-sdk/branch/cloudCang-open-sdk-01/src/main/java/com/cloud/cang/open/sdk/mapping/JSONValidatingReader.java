package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.mapping.json.JSONReader;
import com.cloud.cang.open.sdk.mapping.json.JSONValidator;
import com.cloud.cang.open.sdk.mapping.json.JSONErrorListener;
import com.cloud.cang.open.sdk.mapping.json.StdoutStreamErrorListener;

/**
 * @version 1.0
 * @Description: 自定义json 解析器
 * @Author: zhouhong
 * @Date: 2018/9/3 16:44
 */
public class JSONValidatingReader extends JSONReader {

    public static final Object INVALID = new Object();
    private JSONValidator validator;

    public JSONValidatingReader(JSONValidator validator) {
        this.validator = validator;
    }

    public JSONValidatingReader(JSONErrorListener listener) {
        this(new JSONValidator(listener));
    }

    public JSONValidatingReader() {
        this((JSONErrorListener)(new StdoutStreamErrorListener()));
    }

    public Object read(String string) {
        return !this.validator.validate(string)?INVALID:super.read(string);
    }
}
