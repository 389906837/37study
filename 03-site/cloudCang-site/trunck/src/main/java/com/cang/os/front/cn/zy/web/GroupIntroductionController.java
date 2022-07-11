/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class GroupIntroductionController {
	

	 @RequestMapping("/groupIntroduction")
    public String groupIntroduction(ModelMap map) {
		 
		 return "front/cn/zy/groupIntroduction";
	 }

}
