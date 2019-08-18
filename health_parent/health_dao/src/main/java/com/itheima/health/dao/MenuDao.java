package com.itheima.health.dao;

import java.util.List;
import java.util.Map;

public interface MenuDao {




    List<Map<String,Object>> findAll();

    List<Map<String,Object>> findMenuChildrenByMenuId4Role(Integer id);
}
