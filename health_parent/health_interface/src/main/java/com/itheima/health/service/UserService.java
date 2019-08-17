package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    List<Role> findPage();



    void add(User user, Integer[] roleitemIds);

    PageResult findAllPage(String queryString, Integer currentPage, Integer pageSize);

    User findById(Integer id);

    List<Integer> findRoleItemByUserId(Integer id);

    void update(User user, Integer[] roleitemIds);

    void delete(Integer id);
}
