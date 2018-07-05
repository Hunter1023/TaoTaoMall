package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper itemMapper;
    @Resource
    private TbItemDescMapper itemDescMapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //创建example对象
        TbItemExample example = new TbItemExample();
        //获取分页信息，返回的类型是Page，List的子类
        List<TbItem> list = itemMapper.selectByExample(example);

        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int)pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public TbItem getItemById(Long itemId) {
        return null;
    }

    /**
     * 创建商品
     * @param item 商品
     * @param desc 商品描述
     * @return
     */
    @Override
    public TaotaoResult createItem(TbItem item, String desc) {

        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全TbItem属性
        item.setId(itemId);
        //商品状态，1-正常；2-下架；3-删除
        item.setStatus((byte)1);
        //创建时间和更新时间
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品表
        itemMapper.insert(item);

        //商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //插入商品描述表
        itemDescMapper.insert(itemDesc);

        return TaotaoResult.ok();
    }
}
