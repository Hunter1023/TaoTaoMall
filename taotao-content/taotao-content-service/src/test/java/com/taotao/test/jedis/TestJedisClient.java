package com.taotao.test.jedis;

import com.taotao.content.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author Hunter
 * @Date 18:04 2018/7/8
 */
public class TestJedisClient {

    @Test
    public void testJedisCliPool() {
        //初始化Spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-redis.xml");
        //获取实现类实例
        JedisClient bean = context.getBean(JedisClient.class);
        //调用方法操作
        bean.set("jedisclientkey222", "jedisclientkey222");
        System.out.println(bean.get("jedisclientkey222"));

    }
}
