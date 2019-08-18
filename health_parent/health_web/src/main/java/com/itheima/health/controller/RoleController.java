package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
   private RoleService roleService;

    @RequestMapping("/findAll")
    public Result findAll(){
        Map<String,Object> map = roleService.findAll();
        if (map!=null&&map.size()>0){
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,map);
        }
        return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Role role, Integer[] menuIds,Integer[] permissionIds){
        try {
            roleService.add(role,menuIds,permissionIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
        return new Result(true,MessageConstant.ADD_ROLE_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = roleService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    @RequestMapping("/findPermissionIdsByRoleId")
    public List<Integer> findPermissionIdsByRoleId(Integer id){
        List<Integer> list = roleService.findPermissionIdsByRoleId(id);
        return list;
    }

    @RequestMapping("/findMenuIdsByRoleId")
    public List<Integer> findMenuIdsByRoleId(Integer id){
        List<Integer> list = roleService.findMenuIdsByRoleId(id);
        return list;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Role role = roleService.findById(id);
        if(role!=null){
            Result result = new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(role);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer[] menuIds,Integer[] permissionIds){
        try {
            roleService.edit(role,menuIds,permissionIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_ROLE_SUCCESS);
    }
    @RequestMapping("/delete")
    public Result delete( Integer id){
        try {
            roleService.delete(id);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }
        catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
