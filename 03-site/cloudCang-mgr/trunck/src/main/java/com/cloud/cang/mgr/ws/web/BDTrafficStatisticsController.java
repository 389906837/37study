package com.cloud.cang.mgr.ws.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.ws.service.TrafficStatisticsService;
import com.cloud.cang.mgr.ws.vo.DynamicTrafficStatisticsVo;
import com.cloud.cang.mgr.ws.vo.TrafficStatisticsVo;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 百度人流量统计
 * YUSEN
 */
@Controller
@RequestMapping("/traffic")
public class BDTrafficStatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(BDTrafficStatisticsController.class);
    @Autowired
    private TrafficStatisticsService trafficStatisticsService;


    /**
     * 百度人流量统计
     *
     * @param trafficStatisticsVo
     * @return
     */
    @RequestMapping("/statistics")
    @ResponseBody
    public ResponseVo<String> statistics(@RequestBody TrafficStatisticsVo trafficStatisticsVo) {

        //logger.info("调用百度人流量统计接口开始：{}", JSON.toJSONString(trafficStatisticsVo));
        logger.info("调用百度人流量统计接口开始：{}");
        try {
            if (null == trafficStatisticsVo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计接口,参数为空");
            }
            if (StringUtil.isBlank(trafficStatisticsVo.getBase64Img())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计接口,图片base64为空");
            }
            return trafficStatisticsService.statistics(trafficStatisticsVo);
        } catch (Exception se) {
            logger.error("调用百度人流量统计接口异常：{}", se);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("调用百度人流量统计接口失败");
    }

    /**
     * 百度人流量统计(动态版)
     * 1
     *
     *
     * @param dynamicTrafficStatisticsVo
     * @return
     */
    @RequestMapping("/dynamicStatistics")
    @ResponseBody
    public ResponseVo<String> dynamicStatistics(@RequestBody DynamicTrafficStatisticsVo dynamicTrafficStatisticsVo) {

        //logger.info("调用百度人流量统计-动态版接口开始：{}", JSON.toJSONString(dynamicTrafficStatisticsVo));
        logger.info("调用百度人流量统计-动态版接口开始");
        try {
            if (null == dynamicTrafficStatisticsVo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,参数为空");
            }
            if (StringUtil.isBlank(dynamicTrafficStatisticsVo.getBase64Img())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,图片base64参数为空");
            }
            if (StringUtil.isBlank(dynamicTrafficStatisticsVo.getDynamic())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,dynamic参数为空");
            }
            if (!"true".equals(dynamicTrafficStatisticsVo.getDynamic()) && !"false".equals(dynamicTrafficStatisticsVo.getDynamic())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,dynamic参数异常");
            }
            if ("true".equals(dynamicTrafficStatisticsVo.getDynamic())) {
                if (StringUtil.isBlank(dynamicTrafficStatisticsVo.getCase_id())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,任务ID-case_id参数为空");
                }
                if (StringUtil.isBlank(dynamicTrafficStatisticsVo.getCase_init())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,case_init参数为空");
                }
                if (StringUtil.isBlank(dynamicTrafficStatisticsVo.getArea())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用百度人流量统计-动态版接口,area参数为空");
                }
            }
            return trafficStatisticsService.dynamicStatistics(dynamicTrafficStatisticsVo);
        } catch (Exception se) {
            logger.error("调用百度人流量统计-动态版接口异常：{}", se);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("调用百度人流量统计-动态版接口失败");
    }
}
