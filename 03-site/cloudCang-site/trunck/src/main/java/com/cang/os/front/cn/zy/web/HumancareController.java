/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 人文关怀
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class HumancareController {

	
	 @RequestMapping("/humancare")
    public String humancare(ModelMap map) {
		 
		 return "front/cn/zy/humancare";
	 }

}
