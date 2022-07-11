/**
 * 
 */
package com.cang.os.mgr.sys.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统登录
 * @author hunter
 *
 */
@Controller
@RequestMapping("/mgr")
public class LoginController {
	
    @RequestMapping("/login")
    public String login(ModelMap map) {
    	return "mgr/sys/login";
    }
    
    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }

        return "redirect:/mgr/login";
    }

}
