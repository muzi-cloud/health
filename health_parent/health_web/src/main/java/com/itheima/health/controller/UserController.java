package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author maomao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Reference
    private UserService userService;

    // 从SpringSecurity中获取认证用户的信息
    @RequestMapping(value = "/getUsername")
    public Result getUsername() {
        try {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = user.getUsername();
            List<Map> list = userService.findMenu(username);
            Map<String,Object> map = new HashMap<>();
            map.put("username",username);
            map.put("menu",list);
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,map);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    //注册时,从redis获取可用权限,回显
    @RequestMapping(value = "/findPage")
    public Result findPage() {

        try {
            List<Role> list = userService.findPage();
            return new Result(true, "成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @RequestMapping(value = "/add")
    public Result add(@RequestBody com.itheima.health.pojo.User user, Integer[] roleitemIds) {
        try {
            String encode = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            userService.add(user, roleitemIds);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @RequestMapping(value = "/findById")
    public Result findById(Integer id) {
        try {
            com.itheima.health.pojo.User user = userService.findById(id);
            if (user != null) {
                return new Result(true, "成功", user);
            }
            return new Result(false, "失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @RequestMapping(value = "/findRoleItemByUserId")
    public List<Integer> findRoleItemByUserId(Integer id) {
        return userService.findRoleItemByUserId(id);
    }

    @RequestMapping(value = "/update")
    public Result update(@RequestBody com.itheima.health.pojo.User user, Integer[] roleitemIds) {
        try {
            if (user.getPassword() != null) {
                String encode = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encode);
            }
            userService.update(user, roleitemIds);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    ////
    @RequestMapping(value = "/findAllPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return userService.findAllPage(
                queryPageBean.getQueryString(),
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize());
    }

    @RequestMapping(value = "/delete")
    public Result delete(Integer id) {
        try {
            userService.delete(id);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
