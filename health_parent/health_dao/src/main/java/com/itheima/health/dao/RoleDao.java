package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    void add(Role role);

    void setRoleAndMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    void setRoleAndPermission(@Param("roleId")Integer roleId, @Param("permissionId")Integer permissionId);

    Page<Role> selectByCondition(String queryString);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    Role findById(Integer id);

    void deleteAssociationOfPermission(Integer roleId);

    void deleteAssociationOfMenu(Integer roleId);

    void edit(Role role);

    int findUserCountByRoleId(Integer id);

    void delete(Integer id);
}
