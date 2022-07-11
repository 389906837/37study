package com.cang.os.front.cn.zy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 组织结构
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class OrgStructureController {
	
	@RequestMapping("/orgStructure")
    public String orgStructure(ModelMap map) {
		 
		 return "front/cn/zy/orgStructure";
	 }


}
