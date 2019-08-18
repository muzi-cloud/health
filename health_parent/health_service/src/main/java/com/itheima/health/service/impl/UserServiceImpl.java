package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    // #使用用户名查询用户信息
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public List<Role> findPage() {
        return userDao.findPage();
    }



    @Override
    public void add(User user, Integer[] RoleIds) {

        userDao.add(user);
        setUserAndRole(user.getId(),RoleIds);
    }
    private void setUserAndRole(Integer UserId, Integer[] RoleIds) {
        if(RoleIds!=null && RoleIds.length>0){
            for (Integer RoleId : RoleIds) {
                // 方案一：传递多个参数，在Dao中使用@Param指定每个参数名称
                //UserDao.addCheckGroupAndCheckItem(UserId,checkItemId);
                // 方案二：传递一个Map<String,Integer>，直接使用key指定名称
                Map<String,Integer> map = new HashMap<String,Integer>();
                map.put("UserId",UserId);
                map.put("RoleId",RoleId);
                userDao.addUserAndRole(map);
            }
        }
    }
    @Override
    public PageResult findAllPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = userDao.findAllPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleItemByUserId(Integer id) {
        return userDao.findRoleItemByUserId(id);
    }

    @Override
    public void update(User user, Integer[] roleitemIds) {

        userDao.update(user);
        userDao.deleteUserAndRole(user.getId());
        setUserAndRole(user.getId(),roleitemIds);

    }

    @Override
    public void delete(Integer id) {
        userDao.deleteUserAndRole(id);
        userDao.delete(id);

    }

    @Override
    public List<Map> findMenu(String username) {
        return userDao.findMenu(username);
    }


}
