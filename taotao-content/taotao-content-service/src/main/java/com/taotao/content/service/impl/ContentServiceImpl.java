package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private TbContentMapper contentMapper;
    @Resource
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        //设置分页信息
        PageHelper.startPage(page, rows);

        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    //新增内容
    @Override
    public TaotaoResult saveContent(TbContent content) {

        content.setCreated(new Date());
        content.setUpdated(new Date());

        contentMapper.insertSelective(content);

        try {
            //添加内容时，需要清空此内容所属分类下的所有缓存
            jedisClient.hdel(CONTENT_KEY, content.getCategoryId() + "");
            System.out.println("新增内容时，清空缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok();
    }

    //编辑内容
    @Override
    public TaotaoResult editContent(TbContent content) {
        contentMapper.updateByPrimaryKeyWithBLOBs(content);
        return TaotaoResult.ok();
    }

    //删除内容
    @Override
    public TaotaoResult deleteContent(String ids) {
        String[] idList = ids.split(",");

        for (String idString : idList) {
            Long id = Long.parseLong(idString);
            contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }

    //根据内容分类的id，查询其下的内容列表
    @Override
    public List<TbContent> getContentListByCatId(Long categoryId) {

        //添加缓存不能影响正常的业务逻辑
        try {
            //判断redis中是否有数据，如果有，直接从redis中获取数据返回
            String jsonStr = jedisClient.hget(CONTENT_KEY, categoryId + "");
            if (StringUtils.isNotBlank(jsonStr)) {
                System.out.println("已有缓存");
                return JsonUtils.jsonToList(jsonStr, TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);


        //调用方法将mysql数据库的数据写入redis数据库
        try {
            System.out.println("没有缓存");
            jedisClient.hset(CONTENT_KEY, categoryId + "",
                    JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
