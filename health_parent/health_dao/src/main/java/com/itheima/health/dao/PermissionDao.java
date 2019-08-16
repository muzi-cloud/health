package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface PermissionDao {

    Set<Permission> findPermissionListByRoleId(Integer roleId);
}
