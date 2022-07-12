package com.cloud.cang.mgr.sh.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.sh.service.MerchantClientConfService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;


/**
 * @author ChangTanchang
 * @version 1.0
 * @description 客户端配置
 * @time 2018-03-19
 * @fileName MerchantClientConfController.java
 */
@Controller
@MultipartConfig
@RequestMapping("/merchantClientConf")
public class MerchantClientConfController {

    private static final Logger log = LoggerFactory.getLogger(MerchantClientConfController.class);

    // 客户端配置serv
    @Autowired
    MerchantClientConfService merchantClientConfService;

    @Autowired
    MerchantInfoService merchantInfoService;

    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/edit")
    public String list(ModelMap map) {
        MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        map.put("merchantClientConf", merchantClientConf);
        return "sh/merchantInfo/clientConf-edit";
    }

    /**
     * 客户端配置
     *
     * @param
     * @return
     */
    @RequestMapping("/update")
    public @ResponseBody
    ResponseVo edit(@RequestParam(value = "file", required = false) MultipartFile merLogofile, @RequestParam(value = "loginLogofile", required = false) MultipartFile loginLogofile, MerchantClientConf merchantClientConf) {
        try {
//        if (StringUtils.isBlank((merchantClientConf.getScontactMobile())) || !ValidateUtils.isMobile(merchantClientConf.getScontactMobile())) {
//            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("联系人手机号格式错误！");
//        }
            // 执行修改
            return merchantClientConfService.upMerchant(merLogofile, loginLogofile, merchantClientConf);
        } catch (ServiceException sex) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(sex.getMessage());
        } catch (Exception ex) {
            log.error("客户端配置修改异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("信息配置修改失败");
        }
    }
}