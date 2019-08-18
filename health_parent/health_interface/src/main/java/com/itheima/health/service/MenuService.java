package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {



    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void add(Menu menu);

    Menu findById(Integer id);

    void update(Menu menu);

    void deleteById(Integer id) throws RuntimeException;
}
