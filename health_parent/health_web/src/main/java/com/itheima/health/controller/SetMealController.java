package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.constants.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;

    // 注入JedisPool
    @Autowired
    JedisPool jedisPool;

    // /上传图片（往七牛云上上传）
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile imgFile){
        try {
            // 往七牛云上传递
            // 文件名(04.jpg)
            String fileName = imgFile.getOriginalFilename();
            // 获取文件的后缀名
            String ext = fileName.substring(fileName.lastIndexOf(".")); // .jpg
            String uuidFileName = UUID.randomUUID().toString().toUpperCase();//SDFSDF2324SDFSDFSDFSDFSDFSDFSDFSDFSD
            fileName = uuidFileName+ext; //SDFSDF2324SDFSDFSDFSDFSDFSDFSDFSDFSD.jpg
            // 上传七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //  1：上传图片（向redis中的key值为setmealPicResources 添加数据，图片名称）
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

            // 返回文件名返回
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    // 添加套餐
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Setmeal setmeal,Integer [] checkgroupIds){
        try {
            setMealService.add(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    // 分页查询
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setMealService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;

    }
}
