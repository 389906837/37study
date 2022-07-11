package com.cloud.cang.rec.ws.web;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.rec.cr.service.CameraInfoService;
import com.cloud.cang.rec.sys.util.DateUtil;
import com.cloud.cang.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.*;

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
    @RequestMapping("/heartDetection")
    @ResponseBody
    public String heartDetection( HttpServletRequest request) {
       /* Map<String, String> map =new HashMap<>();
        Enumeration<String> er = request.getParameterNames();
        while (er.hasMoreElements()) {
            String name = (String) er.nextElement();
            String value = request.getParameter(name);
            map.put(name, value);
        }
        System.out.println(map);
        String cameraCode = request.getParameter("cameraCode");
        logger.info("检测相机心跳,相机编号：{}", cameraCode);
        try {
            //iCached.put("heartDetection_" + cameraCode, 60 * 50);
            CameraInfo temp = new CameraInfo();
            temp.setScode(cameraCode);
            temp.setIdelFlag(0);
            CameraInfo cameraInfo = cameraInfoService.selectBySelective(temp);
            if (null != cameraInfo) {
                //
            }
            return cameraCode;
        } catch (Exception e) {
            logger.error("检测相机心跳放入缓存异常：{}", e);
        }
        return "FAIL";*/
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        String name=params.getParameter("cameraCode");
        System.out.println("cameraCode:"+name);

        String cameraCode = request.getParameter("cameraCode");
        return "OK";

    }

    /**
     * 新增相机信息
     *
     * @param cameraInfo
     */
    @RequestMapping("/addCameraInfo")
    public void addCameraInfo(CameraInfo cameraInfo) {
        logger.info("新增相机信息：{}", JSON.toJSONString(cameraInfo));
        cameraInfo.setIdelFlag(0);
        cameraInfo.setTaddTime(DateUtils.getCurrentDateTime());
        cameraInfo.setIstatus(10);
        cameraInfoService.insert(cameraInfo);
    }
}
