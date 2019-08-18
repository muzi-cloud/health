package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Permission;

public interface PermissionService {

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void add(Permission permission);

    Permission findById(Integer id);

    void edit(Permission permission);

    void delete(Integer id);
}
