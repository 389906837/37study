package com.cloud.cang.mgr.common.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.domain.SelectDeviceDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.GroupManageService;
import com.cloud.cang.mgr.sb.vo.DeviceInfoVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.domain.CommodityDomain;
import com.cloud.cang.mgr.sp.domain.SelectCommodityDomain;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sp.vo.CommodityVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.GroupManage;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
