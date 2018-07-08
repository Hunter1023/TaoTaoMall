package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {

    //分页查询
    EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);
    //新增内容
    TaotaoResult saveContent(TbContent content);
    //编辑内容
    TaotaoResult editContent(TbContent content);
    //删除内容
    TaotaoResult deleteContent(String ids);

    //根据内容分类的id，查询其下的内容列表
    List<TbContent> getContentListByCatId(Long categoryId);
}
