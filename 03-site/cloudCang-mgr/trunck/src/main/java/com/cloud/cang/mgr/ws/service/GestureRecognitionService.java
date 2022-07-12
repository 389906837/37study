package com.cloud.cang.mgr.ws.service;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.mgr.ws.util.HttpUtil;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 * Created by YLF on 2020/8/31.
 */
@Service
public class GestureRecognitionService {
    @Autowired
    private ICached iCached;
    @Autowired
    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(FaceDetectService.class);

    /**
     * 获取手势识别结果
     *
     * @param base64Img
     * @return
     */
    public ResponseVo<String> recognition(String base64Img) throws Exception {
       // String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/gesture";
        String url = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "baidu_gesture_recognition_url");
        String ak = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "gesture_detect_ak");
        String accessToken = (String) iCached.get("accessToken_" + ak);
        if (StringUtil.isBlank(accessToken)) {
            String sk = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "gesture_detect_sk");
            accessToken = authService.getAuth(ak, sk);
            if (StringUtil.isBlank(accessToken)) {
                logger.info("获取识别accessToken为空");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("accessToken获取异常");
            }
            iCached.put("accessToken_" + ak, accessToken, 60 * 60 * 24 * 27);
        }
        String imgParam = URLEncoder.encode(base64Img, "UTF-8");
        String param = "image=" + imgParam;
        String result = HttpUtil.post(url, accessToken, param);
           /* GestureRecognitionVo gestureRecognitionVo = JSONObject.parseObject(result, GestureRecognitionVo.class);
            if ("SUCCESS".equals(gestureRecognitionVo.getError_msg()) && 0 == gestureRecognitionVo.getError_code()) {    // 识别成功

            }*/
        return ResponseVo.getSuccessResponse(result);

    }
}
