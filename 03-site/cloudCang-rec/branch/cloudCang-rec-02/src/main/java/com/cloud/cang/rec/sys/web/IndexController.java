package com.cloud.cang.rec.sys.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/rec")
public class IndexController {
    @RequestMapping("/index")
    public String login() {
        //获取登录用户信息
        return "/sys/index";
    }
}
