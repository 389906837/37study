/**
 * 
 */
package com.cang.os.front.cn.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 法律声明
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn")
public class legalNoticeController {
	
	
	
	 @RequestMapping("/legalNotice")
     public String legalNotice(ModelMap map) {
		 
		 return "front/cn/index/legalNotice";
	 }

}
