package com.cloud.cang.vis.ws;

import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 连接测试
 */
@RestController
@RequestMapping("/connTest")
@RegisterRestResource
public class ConnTestService {
    @RequestMapping(value = "/toTest")
    @ResponseBody
    public String toTest() {
        return "200";
    }
}
