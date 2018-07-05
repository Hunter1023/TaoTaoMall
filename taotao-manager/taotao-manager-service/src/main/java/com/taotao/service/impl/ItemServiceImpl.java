package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper itemMapper;
    @Resource
    private TbItemDescMapper itemDescMapper;
    @Resource
    private TbItemParamItemMapper itemParamItemMapper;

    /**
     * 查询商品列表
     *
     * @param page 页码
     * @param rows 每页显示的数量
     */
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
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    /**
     * 查询单个商品
     *
     * @param itemId 商品id
     */
    @Override
    public TbItem getItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 查询商品规格参数
     */
    @Override
    public String getItemParamHtml(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);

        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);

        if (list == null || list.isEmpty()) {
            return "";
        }
        TbItemParamItem itemParamItem = list.get(0);
        String paramData = itemParamItem.getParamData();

        //把json数据转换成java对象，指定List中每一个元素的类型
        List<Map> mapList1 = JsonUtils.jsonToList(paramData, Map.class);
        //遍历list，生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">");
        sb.append(" <tbody>\n");
        for (Map map1 : mapList1) {
            sb.append("     <tr>\n");
            sb.append("         <th class=\"tdTitle\" colspan=\"2\">" + map1.get("group") + "</th>\n");
            sb.append("     </tr>\n");

            List<Map> mapList2 = (List<Map>) map1.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("	    </tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();
    }

    /**
     * 创建商品
     *
     * @param item 商品
     * @param desc 商品描述
     */
    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParam) {

        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全TbItem属性
        item.setId(itemId);
        //商品状态，1-正常；2-下架；3-删除
        item.setStatus((byte) 1);
        //创建时间和更新时间
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品表
        itemMapper.insert(item);

        //商品描述-------------------------------------
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //插入商品描述表
        itemDescMapper.insert(itemDesc);

        //商品参数规格-------------------------------------
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        //插入商品规格参数表
        itemParamItemMapper.insert(itemParamItem);

        return TaotaoResult.ok();
    }
}
