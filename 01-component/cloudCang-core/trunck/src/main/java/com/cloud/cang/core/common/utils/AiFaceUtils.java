package com.cloud.cang.core.common.utils;



import com.baidu.aip.face.AipFace;
import com.cloud.cang.core.common.BaiduAiFaceConfig;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.utils.GrpParaUtil;
import org.apache.commons.lang3.StringUtils;

import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 人脸识别工具类
 *
 * @version 1.0
 * @Author: zengzexiong
 * @Date: 2018年7月25日16:09:13
 */
public class AiFaceUtils {

    private static AipFace client = new AipFace(BaiduAiFaceConfig.AI_APP_ID, BaiduAiFaceConfig.AI_API_KEY, BaiduAiFaceConfig.AI_SECRET_KEY);


    static String GROUP_ID = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "faceGroup");    //数据字典配置




    /**
     * 只有一个用户时对比人脸库
     *
     * @param base64String 图片base64字符串
     * @param userId       用户ID
     * @return
     */
    public static ResponseAip findUser(String base64String, String userId) {
        return findUser(base64String, userId, GROUP_ID);
    }

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
        JSONObject res = client.search(base64String, imageType, groupId, options);
        ResponseAip responseAip = com.alibaba.fastjson.JSONObject.parseObject(res.toString(), ResponseAip.class);
        return responseAip;
    }

    /**
     * 手机号后四位存在多个用户时调用
     * 使用默认分组 GROUP_ID
     *
     * @param base64String 图片base64字符串
     * @return
     */
    public static ResponseAip findUser(String base64String) {
        return findUser(base64String, null);
    }

    /**
     * 用户注册人脸信息
     *
     * @param userId       用户ID
     * @param base64String 图片base64字符串
     * @return
     */
    public static ResponseAip register(String userId, String base64String) {
        return register(userId, base64String, GROUP_ID, null);
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
        JSONObject res = client.addUser(base64String, imageType, groupId, userId, options);
        ResponseAip responseAip = com.alibaba.fastjson.JSONObject.parseObject(res.toString(), ResponseAip.class);
        return responseAip;
    }

    /**
     * 从百度库中删除人脸信息
     *
     * @param userId    用户ID
     * @param faceToken 图片faceToken
     * @return
     */
    public static ResponseAip delete(String userId, String faceToken) {
        return delete(userId, faceToken, GROUP_ID);
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
        JSONObject res = client.faceDelete(userId, groupId, faceToken, options);
        ResponseAip responseAip = com.alibaba.fastjson.JSONObject.parseObject(res.toString(), ResponseAip.class);
        return responseAip;
    }

    /**
     * 将base64字符串转化成图片
     *
     * @param base64Str 图片字符串
     * @param savepath  图片保存地址
     * @return
     */
    public static boolean genetareImg(String base64Str, String savepath) {
        if (base64Str == null) {
            return false;
        }
        // 开始解码
        BASE64Decoder base64Decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            byte[] b = base64Decoder.decodeBuffer(base64Str);
            for (int i = 0; i < b.length; ++i) {
                b[i] += 256;//调整异常数据
            }
            out = new FileOutputStream(savepath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
