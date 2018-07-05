package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 商品相关的处理的service接口
 */
public interface ItemService {

    /**
     * 分页查询
     */
    EasyUIDataGridResult getItemList(Integer page, Integer rows);

    /**
     * 单个商品查询
     * @param itemId
     * @return
     */
    TbItem getItemById(Long itemId);

    /**
     * 新建商品
     */
    TaotaoResult createItem(TbItem item, String desc);

}
