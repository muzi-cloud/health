package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface UserDao {

    User findUserByUsername(String username);

    List<Role> findPage();

    void add(User user);

    void addUserAndRole(Map<String, Integer> map);

    Page<User> findAllPage(String queryString);

    User findById(Integer id);

    List<Integer> findRoleItemByUserId(Integer id);

    void update(User user);

    void deleteUserAndRole(Integer id);

    void delete(Integer id);

    List<Map> findTwoMenuByOneMenu(@Param("id") Integer id, @Param("rid") Integer rid);

    List<Map> findMenu(String username);
}
