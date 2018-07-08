package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ContentController {

    @Resource
    private ContentService contentService;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            Long categoryId) {
        return contentService.getContentList(categoryId, page, rows);
    }

    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveContent(TbContent content) {

        return contentService.saveContent(content);
    }

    @RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult editContent(TbContent content) {

        return contentService.editContent(content);
    }

    @RequestMapping(value = "/content/delete", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContent(String ids) {

        return contentService.deleteContent(ids);
    }



}
