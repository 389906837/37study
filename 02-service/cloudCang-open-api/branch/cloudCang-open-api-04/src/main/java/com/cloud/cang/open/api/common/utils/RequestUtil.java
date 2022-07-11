package com.cloud.cang.open.api.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName: cloudCangBranch
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/9/4 17:22
 */
public class RequestUtil {

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        // 获取组装参数
        Map paramsMap = request.getParameterMap();
        for (Iterator iter = paramsMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) paramsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            map.put(name,valueStr);
        }
        return map;
    }
}
