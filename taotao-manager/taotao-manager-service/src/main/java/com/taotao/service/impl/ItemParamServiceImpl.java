package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Resource
    private TbItemParamMapper itemParamMapper;

    @Override
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {

        //设置分页信息
        PageHelper.startPage(page, rows);
        //创建example对象
        TbItemParamExample example = new TbItemParamExample();
        //获取分页信息，返回的类型是Page，List的子类
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);

        PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public TaotaoResult getItemParamByCid(Long cid) {

        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);

        List<TbItemParam> list = itemParamMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            TbItemParam itemParam = list.get(0);
            return TaotaoResult.ok(itemParam);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(Long cid, String paramData) {
        //创建规格参数对象
        TbItemParam itemParam = new TbItemParam();

        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());

        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItemParam(String ids) {
        String[] idList = ids.split(",");

        for (String idString :
                idList) {
            Long id = Long.parseLong(idString);
            itemParamMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }
}
