package com.itheima.health.dao;

import com.itheima.health.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface RoleDao {

    // 使用用户id，查询用户对应的角色集合
    public Set<Role> findRoleListByUserId(Integer userId);
}
