package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    Map<String,Object> findAll();

    void add(Role role, Integer[] menuIds, Integer[] permissionIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    Role findById(Integer id);

    void edit(Role role, Integer[] menuIds, Integer[] permissionIds);

    void delete(Integer id);
}
