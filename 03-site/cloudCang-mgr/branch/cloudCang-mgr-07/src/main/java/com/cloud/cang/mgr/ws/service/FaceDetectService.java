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

/**
 * 人类检测接口
 * Created by YLF on 2020/8/3.
 */
@Service
public class FaceDetectService {
    @Autowired
    private ICached iCached;
    @Autowired
    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(FaceDetectService.class);


    /**
     * 获取人脸检测结果
     *
     * @param faceJson
     * @return
     */
    public ResponseVo faceDetect(String faceJson) throws Exception {
        // 请求url
       // String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        String url = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "baidu_face_detect_url");
        String ak = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "face_detect_ak");
        String accessToken = (String) iCached.get("accessToken_" + ak);
        if (StringUtil.isBlank(accessToken)) {
            String sk = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "face_detect_sk");
            accessToken = authService.getAuth(ak, sk);
            if (StringUtil.isBlank(accessToken)) {
                logger.info("获取识别accessToken为空");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("accessToken获取异常");
            }
            iCached.put("accessToken_" + ak, accessToken, 60 * 60 * 24 * 27);
        }
        String result = HttpUtil.post(url, accessToken, "application/json", faceJson);
        return ResponseVo.getSuccessResponse(result);
    }
}

