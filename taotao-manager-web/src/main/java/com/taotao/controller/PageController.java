package com.taotao.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示页面
 */
@Controller
public class PageController {

    /**
     * 后台首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    /**
     * 商品查询页面
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {

        return page;
    }

}
