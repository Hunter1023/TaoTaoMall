package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Resource
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 获取内容分类列表
     * @param parentId 父节点id
     */
    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {

        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        List<EasyUITreeNode> nodes = new ArrayList<EasyUITreeNode>();
        for (TbContentCategory contentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setState(contentCategory.getIsParent() ? "closed" : "open");
            node.setText(contentCategory.getName());

            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 添加内容分类
     * @param parentId 父节点的id
     * @param name     节点名
     */
    @Override
    public TaotaoResult createContentCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //新增的节点都是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);

        contentCategoryMapper.insertSelective(contentCategory);//会自动将数据库表中自增的id返回给contentCategory对象

        //判断如果要添加的节点的父节点，本身是叶子节点，需要更新其为父节点
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (parent.getIsParent() == false) {
            parent.setIsParent(true);
            //更新节点的isParent属性为true
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }

        //返回taotaoresult的内容分类id，需要主键返回
        return TaotaoResult.ok(contentCategory);
    }

    //更新内容分类
    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);

        return TaotaoResult.ok();
    }

    //删除内容分类
    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        //判断是否为父节点
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        if(contentCategory.getIsParent()){
            return new TaotaoResult(400, "包含子分类，无法删除", null);
        }

        contentCategoryMapper.deleteByPrimaryKey(id);

        //判断删除后，其父节点是否还有叶子节点，如果没有叶子节点，需要更新父节点为叶子节点
        Long parentId = contentCategory.getParentId();
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);

        //获取和该内容分类 拥有相同父节点 的内容分类数量
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list =  contentCategoryMapper.selectByExample(example);
        //如果没有叶子节点，更新其为叶子节点
        if(list.isEmpty()){
            parent.setIsParent(false);
            //更新节点的isParent属性为true
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }

        return TaotaoResult.ok();
    }
}
