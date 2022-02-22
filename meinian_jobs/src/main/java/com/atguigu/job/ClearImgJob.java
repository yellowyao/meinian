package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.util.qcloudUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    JedisPool jedisPool;
    public void clearImg(){
        System.out.println("==============");
        Set<String> pics = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String pic : pics) {
            qcloudUtils.deleteFileFromQCloud(pic);
            System.out.println("==============++");
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }
    }
}
