package com.taotao.test.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelper {

    @Test
    public void testHelper() {

        //2.初始化Spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-dao.xml");
        //3.获取mapper的代理对象
        TbItemMapper itemMapper = context.getBean(TbItemMapper.class);

        //1.设置分页信息，紧跟的第一个查询才会被分页
        PageHelper.startPage(1, 3);
        //4.调用mapper的方法查询数据
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);

        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);

        //5.遍历结果
        System.out.println(pageInfo.getTotal());

    }
}
