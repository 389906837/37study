package com.cloud.cang.mgr.ws.web;

/**
 * 百度手势识别接口
 * Created by YLF
 * on 2020/8/31.
 */

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.ws.service.GestureRecognitionService;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 百度手势识别
 * YUSEN
 */
@Controller
@RequestMapping("/gesture")
public class BDGestureRecognitionController {
    private static final Logger logger = LoggerFactory.getLogger(BDGestureRecognitionController.class);
    @Autowired
    private GestureRecognitionService gestureRecognitionService;

    /**
     * 百度手势识别接口
     *
     * @param map
     * @return
     */
    @RequestMapping("/recognition")
    @ResponseBody
    public ResponseVo<String> recognition(@RequestBody Map<String, String> map) {
        logger.info("调用百度手势识别接口开始");
        try {
            String base64Img = map.get("base64Img");
            if (StringUtil.isBlank(base64Img)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度手势识别接口,图片base64为空");
            }
            return gestureRecognitionService.recognition(base64Img);
        } catch (Exception se) {
            logger.error("调用百度手势事儿别接口异常：{}", se);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("调用百度手势识别接口失败");
    }
}
