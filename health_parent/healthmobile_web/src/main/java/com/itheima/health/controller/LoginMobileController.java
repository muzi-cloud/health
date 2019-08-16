package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.constants.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/login")
public class LoginMobileController {

    @Reference
    MemberService memberService;

    @Autowired
    JedisPool jedisPool;


    // 提交预约
    @RequestMapping(value = "/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        try {
            // 1：获取页面传递的验证码
            String code = (String)map.get("validateCode");
            // 2：获取页面传递的手机号作为查询条件，查询redis，获取redis中存放的验证码
            String telephone = (String)map.get("telephone");
            String rediskey = telephone+ RedisMessageConstant.SENDTYPE_LOGIN;
            String redisCode = jedisPool.getResource().get(rediskey);
            // 3：使用2个验证码比对
            if(redisCode==null || !redisCode.equals(code)){
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            // 正确
            // 调用Service
            //判断当前用户是否是会员，使用手机号，查询会员
            Member member = memberService.findMemberByTelephone(telephone);
            // * 不是会员，注册会员
            if(member==null){
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            // * 是会员（已经注册会员）
            // * 将会员的信息存放到Cookie（token）-->jwt鉴权
            Cookie cookie = new Cookie("login_name"+telephone,telephone);
            cookie.setPath("/");
            cookie.setMaxAge(30*24*60*60);// 以妙单位，30*24*60*60
            response.addCookie(cookie);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

}
