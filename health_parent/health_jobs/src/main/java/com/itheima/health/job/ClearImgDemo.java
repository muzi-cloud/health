package com.itheima.health.job;

import com.itheima.health.constants.RedisConstant;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName ClearImgDemo
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/5 18:27
 * @Version V1.0
 */
public class ClearImgDemo {

    @Autowired
    JedisPool jedisPool;

    /**
     * 二：比较redis中的2个key，找出不同的图片
     * key：setmealPicResources
     * key：setmealPicDbResources
     */
    public void run(){
        // 比较查找2个key中不同的图片名称，并获取集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            String fileName = iterator.next();
            // 垃圾图片的名称
            System.out.println("垃圾图片的名称:"+fileName);
            // 删除七牛云上的垃圾图片
            QiniuUtils.deleteFileFromQiniu(fileName);
            // 删除redis中key值为RedisConstant.SETMEAL_PIC_RESOURCES的图片信息
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        }
    }
}
