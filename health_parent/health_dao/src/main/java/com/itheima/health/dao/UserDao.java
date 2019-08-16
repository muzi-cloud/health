package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface UserDao {

    User findUserByUsername(String username);
}
