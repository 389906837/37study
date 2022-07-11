package com.cloud.cang.wap.index.web;


import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * 访问权限
 */
@Controller
@RequestMapping("/visitor")
public class VisitorController {
	private static Logger logger = LoggerFactory.getLogger(VisitorController.class);
	/**
	 * 没有权限
	 * @return
	 */
	@RequestMapping("/unpermissions")
	public ModelAndView page(HttpServletRequest request) throws Exception {
		String sip = RequestUtils.getIpAddr(request);
		logger.info("没有权限，不能访问，访问IP：{}", sip);
		return new ModelAndView("visitor/unpermissions");
	}
}
