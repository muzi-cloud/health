package com.itheima.health.dao;

import com.itheima.health.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuDao {




    List<Map<String,Object>> findAll();

    List<Map<String,Object>> findMenuChildrenByMenuId4Role(Integer id);



    List<Menu> findMenuByCondition(String queryString);

    void add(Menu menu);

    Menu findById(Integer id);

    void update(Menu menu);

    int findCountByMenu(Integer id);

    void deleteById(Integer id);
}
