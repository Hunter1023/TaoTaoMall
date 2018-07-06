package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
    //通过节点的id，查询该节点的子节点列表
    List<EasyUITreeNode> getContentCategoryList(Long parentId);
    //添加内容分类节点
    TaotaoResult createContentCategory(Long parentId, String name);
    //更新内容分类节点
    TaotaoResult updateContentCategory(Long id, String name);
    //删除内容分类节点
    TaotaoResult deleteContentCategory(Long id);
}
