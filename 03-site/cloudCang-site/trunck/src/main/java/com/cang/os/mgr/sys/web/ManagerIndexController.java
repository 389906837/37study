/**
 * 
 */
package com.cang.os.mgr.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/mgr")
public class ManagerIndexController {
	
    @RequestMapping("/index")
    public String index(ModelMap map) {
    	
    	return "mgr/sys/index";
    }
    
    /**
     * 操作成功
     * @param map
     * @return
     */
    @RequestMapping("/success")
    public String success(ModelMap map,String backUri) {
    	
    	 map.put("backUri", backUri);
		 return "mgr/success";
    }

}
