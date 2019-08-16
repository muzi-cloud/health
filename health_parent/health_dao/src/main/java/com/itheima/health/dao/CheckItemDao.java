package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface CheckItemDao {
    void add(CheckItem checkItem);

    List<CheckItem> findCheckItemByCondition(String queryString);

    void deleteById(Integer id);

    int findCountByCheckItemId(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();

    List<CheckItem> findCheckItemListByCheckGroupId(Integer id);
}
