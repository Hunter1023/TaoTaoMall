package com.taotao.test.jedis;

import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Hunter
 * @Date 15:12 2018/7/8
 */
public class testJedis {

    // 测试单机版，连接池
    @Test
    public void testJedisPool() {
        //设置连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //连接池中最大连接数
        config.setMaxTotal(50);
        //空闲时保有的最大连接数
        config.setMaxIdle(10);

        //设置连接池对象
        JedisPool pool = new JedisPool(config, "192.168.37.128", 6379);
        //从连接池中获取连接对象
        Jedis jedis = pool.getResource();
        //直接操作redis set
        jedis.set("key12345", "value");
        System.out.println(jedis.get("key12345"));
        //将对象放回连接池
        jedis.close();
        //关闭连接池（应用系统关闭时才关闭）
        pool.close();
    }

    // 测试集群版
    @Test
    public void testJedisCluster() throws IOException {

        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.37.128", 7001));
        nodes.add(new HostAndPort("192.168.37.128", 7002));
        nodes.add(new HostAndPort("192.168.37.128", 7003));
        nodes.add(new HostAndPort("192.168.37.128", 7004));
        nodes.add(new HostAndPort("192.168.37.128", 7005));
        nodes.add(new HostAndPort("192.168.37.128", 7006));

        //创建jedisCluster对象（封装了连接池）
        JedisCluster cluster = new JedisCluster(nodes);
        //根据jedisCluster对象操作redis集群
        cluster.set("keycluster", "cluster的value");
        System.out.println(cluster.get("keycluster"));
        //关闭jediscluster对象（应用系统关闭时才关闭）
        cluster.close();
    }

}
