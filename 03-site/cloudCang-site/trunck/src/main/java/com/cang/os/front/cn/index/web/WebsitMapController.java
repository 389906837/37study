/**
 * 
 */
package com.cang.os.front.cn.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn")
public class WebsitMapController {
	
	
	 @RequestMapping("/websitMap")
     public String websitMap(ModelMap map) {
		 
		 return "front/cn/index/websitMap"; 
	 }

}
