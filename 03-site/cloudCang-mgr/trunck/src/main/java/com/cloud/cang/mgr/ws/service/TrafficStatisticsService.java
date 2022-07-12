package com.cloud.cang.mgr.ws.service;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.mgr.ws.util.HttpUtil;
import com.cloud.cang.mgr.ws.vo.DynamicTrafficStatisticsVo;
import com.cloud.cang.mgr.ws.vo.TrafficStatisticsVo;
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
public class TrafficStatisticsService {
    @Autowired
    private ICached iCached;
    @Autowired
    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(FaceDetectService.class);

    /**
     * 百度人流量统计
     *
     * @param trafficStatisticsVo
     * @return
     */
    public ResponseVo<String> statistics(TrafficStatisticsVo trafficStatisticsVo) throws Exception {
       // String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_num";
        String url = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "baidu_traffic_statistics_url");
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
        String base64Img = trafficStatisticsVo.getBase64Img();
        String imgParam = URLEncoder.encode(base64Img, "UTF-8");
        StringBuffer param = new StringBuffer();
        param.append("image=" + imgParam);
        if (StringUtil.isNotBlank(trafficStatisticsVo.getShow())) {
            param.append("&show=" + trafficStatisticsVo.getShow());
        }
        if (StringUtil.isNotBlank(trafficStatisticsVo.getArea())) {
            param.append("&area=" + trafficStatisticsVo.getArea());
        }
        String result = HttpUtil.post(url, accessToken, param.toString());
           /* GestureRecognitionVo gestureRecognitionVo = JSONObject.parseObject(result, GestureRecognitionVo.class);
            if ("SUCCESS".equals(gestureRecognitionVo.getError_msg()) && 0 == gestureRecognitionVo.getError_code()) {    // 识别成功

            }*/
        return ResponseVo.getSuccessResponse(result);

    }

    /**
     * 百度人流量统计(动态版)
     *
     * @param dynamicTrafficStatisticsVo
     * @return
     */
    public ResponseVo<String> dynamicStatistics(DynamicTrafficStatisticsVo dynamicTrafficStatisticsVo) throws Exception {
      //  String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_tracking";
        String url = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "baidu_traffic_dynamicStatistics_url");
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
        String base64Img = dynamicTrafficStatisticsVo.getBase64Img();
        String imgParam = URLEncoder.encode(base64Img, "UTF-8");
        StringBuffer param = new StringBuffer();
        param.append("image=" + imgParam);
        param.append("&dynamic=" + dynamicTrafficStatisticsVo.getDynamic());
        if (StringUtil.isNotBlank(dynamicTrafficStatisticsVo.getCase_id())) {
            param.append("&case_id=" + dynamicTrafficStatisticsVo.getCase_id());
        }
        if (StringUtil.isNotBlank(dynamicTrafficStatisticsVo.getCase_init())) {
            param.append("&case_init=" + dynamicTrafficStatisticsVo.getCase_init());
        }
        if (StringUtil.isNotBlank(dynamicTrafficStatisticsVo.getShow())) {
            param.append("&show=" + dynamicTrafficStatisticsVo.getShow());
        }
        if (StringUtil.isNotBlank(dynamicTrafficStatisticsVo.getArea())) {
            param.append("&area=" + dynamicTrafficStatisticsVo.getArea());
        }
        String result = HttpUtil.post(url, accessToken, param.toString());
           /* GestureRecognitionVo gestureRecognitionVo = JSONObject.parseObject(result, GestureRecognitionVo.class);
            if ("SUCCESS".equals(gestureRecognitionVo.getError_msg()) && 0 == gestureRecognitionVo.getError_code()) {    // 识别成功
            }*/
        return ResponseVo.getSuccessResponse(result);

    }
}
