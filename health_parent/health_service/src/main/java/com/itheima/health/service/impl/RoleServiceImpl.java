package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public Map<String, Object> findAll() {
        Map<String,Object> map = new HashMap<>();
        List<Permission> permissionTableData= permissionDao.findAll();
        List<Map<String,Object>> menuTableData = menuDao.findAll();
        for (Map<String, Object> menuLevelOne : menuTableData) {
            List<Map<String, Object>> menuChildrenList = menuDao.findMenuChildrenByMenuId4Role((Integer) menuLevelOne.get("id"));
            menuLevelOne.put("children",menuChildrenList);
        }

        map.put("permissionTableData",permissionTableData);
        map.put("menuTableData",menuTableData);
            return map;
    }

    @Override
    public void add(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.add(role);
        setRoleAndMenu(role.getId(),menuIds);
        setRoleAndPermission(role.getId(),permissionIds);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Role> page = roleDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public void edit(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.deleteAssociationOfPermission(role.getId());
        roleDao.deleteAssociationOfMenu(role.getId());
        setRoleAndMenu(role.getId(),menuIds);
        setRoleAndPermission(role.getId(),permissionIds);
        roleDao.edit(role);

    }

    @Override
    public void delete(Integer id) {
        int count = roleDao.findUserCountByRoleId(id);
        if(count>0){
            throw new RuntimeException("当前角色被引用,不能删除");
        }else{
            roleDao.deleteAssociationOfMenu(id);
            roleDao.deleteAssociationOfPermission(id);
            roleDao.delete(id);
        }
    }

    private void setRoleAndPermission(Integer roleId, Integer[] permissionIds) {
        if(permissionIds!=null&&permissionIds.length>0){
            for (Integer permissionId : permissionIds) {
                roleDao.setRoleAndPermission(roleId,permissionId);
            }
        }
    }

    private void setRoleAndMenu(Integer roleId, Integer[] menuIds) {
        if(menuIds!=null&&menuIds.length>0){
            for (Integer menuId : menuIds) {
                roleDao.setRoleAndMenu(roleId,menuId);
            }
        }
    }
}