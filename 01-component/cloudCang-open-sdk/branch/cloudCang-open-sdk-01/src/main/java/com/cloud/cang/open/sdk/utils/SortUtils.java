package com.cloud.cang.open.sdk.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description: Map排序工具类
 * @Author: zhouhong
 * @Date: 2016年2月23日 下午5:13:12
 * @version 1.0
 */
public class SortUtils {

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
}



class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        if (str1.indexOf("_response") != -1) {
            return -1;
        }
        if (str2.indexOf("_response") != -1) {
            return 1;
        }
        if (str1.equals("sign")) {
            return -1;
        }
        if (str2.equals("sign")) {
            return 1;
        }
        return str1.compareTo(str2);
    }
}
