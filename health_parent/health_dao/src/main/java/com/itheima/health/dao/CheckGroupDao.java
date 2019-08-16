package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    //void addCheckGroupAndCheckItem(@Param(value = "checkGroupId") Integer checkGroupId, @Param(value = "checkItemId") Integer checkItemId);

    void addCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemByCheckGroupId(Integer id);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupAndCheckItemByCheckGroupId(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupListBySetmealId(Integer id);
}
