package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;
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
public interface MenuDao {

    List<Menu> findMenuByCondition(String queryString);

    void add(Menu menu);

    Menu findById(Integer id);

    void update(Menu menu);

    int findCountByMenu(Integer id);

    void deleteById(Integer id);
}
