package com.itheima.health.controller;

import com.itheima.health.constants.MessageConstant;
import com.itheima.health.constants.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/validateCode")
public class ValidateCodeMobileController {

    @Autowired
    JedisPool jedisPool;

    // 发送验证码
    @RequestMapping(value = "/send4Order")
    public Result send4Order(String telephone){
        try {
            // 1：生成4位验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            // 2：调用SMSUtils发送短信
            // SMSUtils.sendShortMessage("SMS_165692413",telephone,code.toString());
            System.out.println("验证码："+code.toString());
            // 3：往redis中存储数据(key:13112121212001,value:1234)
            jedisPool.getResource().setex(
                    telephone+ RedisMessageConstant.SENDTYPE_ORDER,
                    300,// 5分秒
                    code.toString()
            );
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    // 发送验证码（在登录）
    @RequestMapping(value = "/send4Login")
    public Result send4Login(String telephone){
        try {
            // 1：生成4位验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            // 2：调用SMSUtils发送短信
            // SMSUtils.sendShortMessage("SMS_165692413",telephone,code.toString());
            System.out.println("验证码："+code.toString());
            // 3：往redis中存储数据(key:13112121212002,value:1234)
            jedisPool.getResource().setex(
                    telephone+ RedisMessageConstant.SENDTYPE_LOGIN,
                    300,// 5分秒
                    code.toString()
            );
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
