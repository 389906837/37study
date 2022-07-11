package com.cloud.cang.core.common.utils;

import com.baidu.aip.face.AipFace;
import com.cloud.cang.core.common.BaiduAiFaceConfig;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.utils.GrpParaUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 年会人脸识别工具类
 *
 * @version 1.0
 * @Author: zengzexiong
 * @Date: 2018年12月13日11:44:22
 */
public class AmFaceUtils {

    private static AipFace amClient = new AipFace(BaiduAiFaceConfig.AM_APP_ID, BaiduAiFaceConfig.AM_API_KEY, BaiduAiFaceConfig.AM_SECRET_KEY);

    /**
     * 年会人脸图片分组
     */
    public static String AM_GROUP_ID = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "groupID");    //数据字典配置

    /**
     * 只有一个用户时对比人脸库
     *
     * @param base64String 图片
     * @param userId       用户ID
     * @param groupId      分组ID
     * @return
     */
    public static ResponseAip findUser(String base64String, String userId, String groupId) {
        String imageType = "BASE64";
        HashMap<String, String> options = new HashMap<String, String>();
        options.put(BaiduAiFaceConfig.QUALITY_CONTROL, "NORMAL");
        options.put(BaiduAiFaceConfig.LIVENESS_CONTROL, "LOW");
        if (StringUtils.isNotBlank(userId)) {
            options.put(BaiduAiFaceConfig.USER_ID, userId);
        }
        // 人脸注册
        JSONObject res = amClient.search(base64String, imageType, groupId, options);
        ResponseAip responseAip = com.alibaba.fastjson.JSONObject.parseObject(res.toString(), ResponseAip.class);
        return responseAip;
    }


    /**
     * 用户注册人脸信息
     *
     * @param userId       用户ID
     * @param base64String 图片base64字符串
     * @param groupId      百度图片分组ID
     * @return
     */
    public static ResponseAip register(String userId, String base64String, String groupId, String userInfo) {
        String imageType = "BASE64";
        HashMap<String, String> options = new HashMap<String, String>();
        options.put(BaiduAiFaceConfig.USER_INFO, userId);        // 用户资料（选填）
        options.put(BaiduAiFaceConfig.QUALITY_CONTROL, "NORMAL");  // 图片质量控制（选填）
        options.put(BaiduAiFaceConfig.LIVENESS_CONTROL, "LOW");    // 活体检测控制（选填）
        options.put("user_info", userInfo);                        // 用户资料，长度限制256B

        // 调用百度人脸注册
        JSONObject res = amClient.addUser(base64String, imageType, groupId, userId, options);
        ResponseAip responseAip = com.alibaba.fastjson.JSONObject.parseObject(res.toString(), ResponseAip.class);
        return responseAip;
    }

    /**
     * 从百度库中给删除人脸信息
     *
     * @param userId    用户ID
     * @param faceToken 图片faceToken
     * @param groupId   百度分组ID
     * @return
     */
    public static ResponseAip delete(String userId, String faceToken, String groupId) {
        HashMap<String, String> options = new HashMap<String, String>();
        // 调用百度人脸识别API
        JSONObject res = amClient.faceDelete(userId, groupId, faceToken, options);
        ResponseAip responseAip = com.alibaba.fastjson.JSONObject.parseObject(res.toString(), ResponseAip.class);
        return responseAip;
    }
}
