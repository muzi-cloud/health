package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SpringSecurityUserService
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/12 14:59
 * @Version V1.0
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    /*
        String username：页面输入的登录名
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 使用登录名去查询用户名，获取用户信息，用户信息具有多个角色，多个权限
        com.itheima.health.pojo.User user = userService.findUserByUsername(username); // admin
        // 没有用户信息，登录名输入有误
        if(user==null){
            return null; // 抛出异常，登录名输入有误
        }
        // 获取权限，封装到List<GrantedAuthority>，授权
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            // list.add(new SimpleGrantedAuthority(role.getKeyword())); // ROLE_ADMIN
            if(role!=null && role.getPermissions()!=null && role.getPermissions().size()>0){
                for (Permission permission : role.getPermissions()) {
                    list.add(new SimpleGrantedAuthority(permission.getKeyword())); // CHECKITEM_ADD；CHECKITEM_DELETE
                }
            }
        }
        // 参数一：登录名；参数二：使用数据库查询的密码（BCryptPasswordEncoding）；参数三：权限
        return new User(username,user.getPassword(),list);
    }
}
