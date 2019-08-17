package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.constants.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/order")
public class OrderMobileController {

    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;


    // 提交预约
    @RequestMapping(value = "/submit")
    public Result getSetmeal(@RequestBody Map map){
        try {
            // 1：获取页面传递的验证码
            String code = (String)map.get("validateCode");
            // 2：获取页面传递的手机号作为查询条件，查询redis，获取redis中存放的验证码
            String telephone = (String)map.get("telephone");
            String rediskey = telephone+ RedisMessageConstant.SENDTYPE_ORDER;
            String redisCode = jedisPool.getResource().get(rediskey);
            // 3：使用2个验证码比对
            if(redisCode==null || !redisCode.equals(code)){
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            // 正确
            Result result = null;
            map.put("orderType", Order.ORDERTYPE_WEIXIN); // 说明执行微信公众号进行的预约
            result = orderService.submitOrder(map);
            // 调用Service
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    // 使用订单id作为查询条件，查询订单信息，使用联合查询，要的不是id，而是名称，返回Map结构
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
