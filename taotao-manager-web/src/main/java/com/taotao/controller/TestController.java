package com.taotao.controller;

import com.taotao.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 测试使用的Controller，查询当前时间
 */
@Controller
public class TestController {

    /**
     * 通过dubbo获取
     */
    @Resource
    private TestService testService;

    @RequestMapping("/test/queryNow")
    @ResponseBody
    public String queryNow() {
        return testService.queryNow();
    }
}
