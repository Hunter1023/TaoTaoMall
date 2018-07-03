package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;

/**
 * 商品相关的处理的service接口
 */
public interface ItemService {

    /**
     * 分页查询
     */
    EasyUIDataGridResult getItemList(Integer page, Integer rows);

}
