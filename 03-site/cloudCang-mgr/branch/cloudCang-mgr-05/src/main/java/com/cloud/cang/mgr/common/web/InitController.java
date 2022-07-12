package com.cloud.cang.mgr.common.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zhouhong
 * @Description: 初始化数据
 * @Date: 2018/2/9 16:18
 */
@Controller
@RequestMapping("/init")
public class InitController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(InitController.class);
    @Autowired
    private MerchantInfoService merchantInfoService;
    /**
     * @Author: yanlingfeng
     * @Description: 获取设备分组信息
     * @param: deviceIds 设备分组ID集合
     * @Date: 2018/2/27 11:00
     */
    @RequestMapping("/cached")
    @ResponseBody
    ResponseVo<String> initCached() {
        logger.info("初始化缓存开始...");
        merchantInfoService.initCached();
        return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("cached.init.success"));
    }

}
