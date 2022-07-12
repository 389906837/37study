
package com.cloud.cang.mgr.common;


import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;
import java.util.Map;


/**
 * 请严格准命名规范
 * ERROR_系统代号_错误类型 <br />
 * 1001 -2000 通用错误码
 * 2001 - 2500 MSC系统错误码
 *
 * @author zhouhong
 * @version 2.0
 */
public enum BDErrorCodeEnum {
    liveness_check_fail(223120, "未检测到有效人脸"),
    pic_not_has_face(222202, "图片中没有人脸"),
    low_quality(222350, "质量过低"),
    incomplete_face(223116, "人脸不完整"),
    network_not_available(222201, "服务端请求失败"),
    image_check_fail(222203, "无法解析人脸"),
    image_url_download_fail(222204, "从图片的url下载图片失败"),
    add_face_fail(222300, "人脸图片添加失败"),
    ERROR_COMMON_SYSTEM(1002, "系统错误");

    private int code;
    String returnMsg;

    BDErrorCodeEnum(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return this.code;
    }

    public <T> ResponseVo<T> getResponseVo(T t) {
        return new ResponseVo(false, this.code, this.returnMsg, t);
    }

    public ResponseVo getResponseVo() {
        return new ResponseVo(false, this.code, this.returnMsg);
    }

    public ResponseVo getResponseVo(String msg) {
        return new ResponseVo(false, this.code, msg);
    }

    private static Map<Integer, BDErrorCodeEnum> params;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (params == null) {
                params = new HashMap<Integer, BDErrorCodeEnum>();
                for (BDErrorCodeEnum e : BDErrorCodeEnum.values()) {
                    params.put(e.code, e);
                }
            }
        }
    }


    public static String getNameByCode(int code) {
        return params.get(code).returnMsg;
    }

    public static BDErrorCodeEnum getEnumByCode(int code) {
        return params.get(code);
    }

}
