package com.cloud.cang.core.common;

import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName BaiduAiFaceErrorCodeEnum
 * @Description 百度人脸识别返回错误码
 * @Author zengzexiong
 * @Date 2018年8月7日15:52:48
 */
public enum BaiduAiFaceErrorCodeEnum {
    OPEN_API_REQUEST_LIMIT_REACHED(4, "Open api request limit reached"),   // 集群超限额
    IAM_CERTIFICATION_FAILED(14, "IAM Certification failed"),   // IAM鉴权失败，建议用户参照文档自查生成sign的方式是否正确，或换用控制台中ak sk的方式调用
    OPEN_API_DAILY_REQUEST_LIMIT_REACHED(17, "Open api daily request limit reached"),   // 每天流量超限额
    OPEN_API_QPS_REQUEST_LIMIT_REACHED(18, "Open api qps request limit reached"),   // QPS超限额
    OPEN_API_TOTAL_REQUEST_LIMIT_REACHED(19, "Open api total request limit reached"),   // 请求总量超限额
    INVALID_PARAMETER(100, "Invalid parameter"),   // 无效参数
    ACCESS_TOKEN_INVALID_OR_NO_LONGER_VALID(110, "Access token invalid or no longer valid"),   // Access Token失效
    ACCESS_TOKEN_EXPIRED(111, "Access token expired"),   // Access token过期
    NETWORK_NOT_AVAILABLEL(222205, "network not availablel"),   // 服务端请求失败
    RTSE_SERVICE_RETURN_FAIL(222206, "rtse service return fail"), // 服务端请求失败
    MATCH_USER_IS_NOT_FOUND(222207, "match user is not found"),  // 未找到匹配的用户
    THE_NUMBER_OF_IMAGE_IS_INCORRECT(222208, "the number of image is incorrect"),  //图片的数量错误
    FACE_TOKEN_NOT_EXIST(222209, "face token not exist"),  // face token不存在
    ADD_FACE_FAIL(222300, "add face fail"),  // 人脸图片添加失败
    GET_FACE_FAIL(222301, "get face fail"),  // 获取人脸图片失败
    SYSTEM_ERROR(222302, "system error"),  // 服务端请求失败
    GET_FACE_FAIL_(222303, "get face fail"),  // 获取人脸图片失败
    GROUP_IS_NOT_EXIST(223100, "group is not exist"),  // 操作的用户组不存在
    GROUP_IS_ALREADY_EXIST(223101, "group is already exist"),  // 该用户组已存在
    USER_IS_ALREADY_EXIST(223102, "user is already exist"),  // 该用户已存在
    USER_IS_NOT_EXIST(223103, "user is not exist"),  // 找不到该用户
    GROUP_LIST_IS_TOO_LARGE(223104, "group_list is too large"),  // group_list包含组数量过多
    FACE_IS_ALREADY_EXIST(223105, "face is already exist"),  // 该人脸已存在
    FACE_IS_NOT_EXIST(223106, "face is not exist"),  // 该人脸不存在
    UID_LIST_IS_TOO_LARGE(223110, "uid_list is too large"),  // uid_list包含数量过多
    DST_GROUP_IS_NOT_EXIST(223111, "dst group is not exist"),  // 目标用户组不存在
    QUALITY_CONF_FORMAT_ERROR(223112, "quality_conf format error"),  // quality_conf格式不正确
    FACE_IS_COVERED(223113, "face is covered"),  // 人脸有被遮挡
    FACE_IS_FUZZY(223114, "face is fuzzy"),  // 人脸模糊
    FACE_LIGHT_IS_NOT_GOOD(223115, "face light is not good"),  // 人脸光照不好
    INCOMPLETE_FACE(223116, "incomplete face"),  // 人脸不完整
    APP_LIST_IS_TOO_LARGE(223117, "app_list is too large"),  // app_list包含app数量过多
    QUALITY_CONTROL_ERROR(223118, "quality control error"),  // 质量控制项错误
    LIVENESS_CONTROL_ITEM_ERROR(223119, "liveness control item error"),  // 活体控制项错误
    LIVENESS_CHECK_FAIL(223120, "liveness check fail"),  // 活体检测未通过
    LEFT_EYE_IS_OCCLUSION(223121, "left eye is occlusion"),  // 质量检测未通过 左眼遮挡程度过高
    RIGHT_EYE_IS_OCCLUSION(223122, "right eye is occlusion"),  // 质量检测未通过
    LEFT_CHEEK_IS_OCCLUSION(223123, "left cheek is occlusion"),  // 质量检测未通过
    RIGHT_CHEEK_IS_OCCLUSION(223124, "right cheek is occlusion"),  // 质量检测未通过
    CHIN_CONTOUR_IS_OCCLUSION(223125, "chin contour is occlusion"),  // 质量检测未通过 下巴遮挡程度过高
    NOSE_IS_OCCLUSION(223126, "nose is occlusion"),  // 质量检测未通过 鼻子遮挡程度过高
    MOUTH_IS_OCCLUSION(223127, "mouth is occlusion"),  // 质量检测未通过 嘴巴遮挡程度过高






    ERROR_COMMON_PARAM(10086, "服务端请求失败");

    private int code;
    String returnMsg;

    BaiduAiFaceErrorCodeEnum(int code, String returnMsg) {
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

    private static Map<Integer, BaiduAiFaceErrorCodeEnum> params;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (params == null) {
                params = new HashMap<Integer, BaiduAiFaceErrorCodeEnum>();
                for (BaiduAiFaceErrorCodeEnum e : BaiduAiFaceErrorCodeEnum.values()) {
                    params.put(e.code, e);
                }
            }
        }
    }


    public static String getNameByCode(int code) {
        return params.get(code).returnMsg;
    }

    public static BaiduAiFaceErrorCodeEnum getEnumByCode(int code) {
        return params.get(code);
    }
}
