package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //创建example对象
        TbItemExample example = new TbItemExample();
        //获取分页信息，返回的类型是Page，List的子类
        List<TbItem> list = tbItemMapper.selectByExample(example);

        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int)pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }
}
