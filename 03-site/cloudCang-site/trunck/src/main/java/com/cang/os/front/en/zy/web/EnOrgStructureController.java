/**
 * 
 */
package com.cang.os.front.en.zy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hunter
 *
 */

@Controller
@RequestMapping("/en/zy")
public class EnOrgStructureController {
	
	@RequestMapping("/orgStructure")
    public String orgStructure(ModelMap map) {
		 
		 return "front/en/zy/enOrgStructure";
	 }


}
