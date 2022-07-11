/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 团队介绍
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class TeamController {
	

   @RequestMapping("/team")
   public String team(ModelMap map) {
		 
		 return "front/cn/zy/team";
   }
	
	

}
