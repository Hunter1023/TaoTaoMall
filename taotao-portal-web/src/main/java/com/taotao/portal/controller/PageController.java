package com.taotao.portal.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @Resource
    private ContentService contentService;

    @Value("${AD1_CATEGORY_ID}")
    private Long CATEGORY_ID;

    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;
    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;

    @Value("${AD1_WIDTH_B}")
    private String AD1_WIDTH_B;
    @Value("${AD1_HEIGHT_B}")
    private String AD1_HEIGHT_B;

    @RequestMapping("/index")
    public String showIndex(Model model) {

        List<TbContent> contentList = contentService.getContentListByCatId(CATEGORY_ID);

        //转换成自定义的pojo列表 Ad1Node列表
        List<Ad1Node> nodes = new ArrayList<Ad1Node>();

        for (TbContent tbContent : contentList) {
            Ad1Node node = new Ad1Node();

            node.setAlt(tbContent.getSubTitle());
            node.setWidth(AD1_WIDTH);
            node.setHeight(AD1_HEIGHT);
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setWidth(AD1_WIDTH_B);
            node.setHeightB(AD1_HEIGHT_B);

            nodes.add(node);
        }
        //根据前端页面对应的String设置，
        model.addAttribute("ad1", JsonUtils.objectToJson(nodes));
        return "index";
    }
}
