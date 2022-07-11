package com.cang.os.front.cn.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 *
 * @author zhouhong
 */
@Controller
@RequestMapping("/ziyu")
public class ZiyuController {
    /**
     * 董事长致词
     *
     * @return
     */
    @RequestMapping("/index")
    public String temp() {
        return "front/cn/index/ziyu";
    }


}
