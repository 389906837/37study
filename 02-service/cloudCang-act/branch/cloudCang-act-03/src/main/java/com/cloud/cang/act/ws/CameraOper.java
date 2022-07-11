package com.cloud.cang.act.ws;

import com.cloud.cang.act.cr.service.CameraInfoService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by YLF on 2020/5/25.
 */

@Controller
@RequestMapping("/cameraOper")
public class CameraOper {
    @Autowired
    private CameraInfoService cameraInfoService;
    @Autowired
    private ICached iCached;
    private static final Logger logger = LoggerFactory.getLogger(CameraOper.class);

    /**
     * 检测相机心跳
     *
     * @param request
     */
    @RequestMapping(value = "/heartDetection", method = RequestMethod.POST)
    @ResponseBody
    public String heartDetection(HttpServletRequest request) {
        String cameraCode = request.getParameter("cameraCode");
        logger.info("检测相机心跳,相机编号：{}", cameraCode);
        try {
            //iCached.put("HEART_DETECTION_" + cameraCode, cameraCode, 60 * 1);
            iCached.hset("CAMERA_INFO", "HEART_DETECTION_" + cameraCode, DateUtils.getCurrentDateTime());
            return "OK";
        } catch (Exception e) {
            logger.error("检测相机心跳放入缓存异常：{}", e);
        }
        return "FAIL";
    }

    /**
     * 初始化相机
     *
     * @param request
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    public String register(HttpServletRequest request) {
        String cameraCode = request.getParameter("cameraCode");
        logger.info("相机首次注册，相机编号：{}", cameraCode);
        CameraInfo cameraInfo = cameraInfoService.selectByCode(cameraCode);
        if (null != cameraInfo) {
            CameraInfo temp = new CameraInfo();
            temp.setId(cameraInfo.getId());
            temp.setIstatus(10);
            temp.setTregisterTime(DateUtils.getCurrentDateTime());
            cameraInfoService.updateBySelective(temp);
            return "OK";
        }
        return "FAIL";
    }
}
