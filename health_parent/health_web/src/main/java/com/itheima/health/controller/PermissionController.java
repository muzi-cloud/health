package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {
   @Reference
    private PermissionService permissionService;

   @RequestMapping("/findPage")
   public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult = permissionService.pageQuery(
               queryPageBean.getCurrentPage(),
               queryPageBean.getPageSize(),
               queryPageBean.getQueryString()
       );
       return pageResult;
   }
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
       Permission permission = permissionService.findById(id);
       if(permission==null){
           return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
       }
       return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS,permission);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
    }
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.delete(id);
        }catch (RuntimeException e){
        return new Result(false,e.getMessage());
    }
            catch (Exception e) {
        return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
    }
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
}



}
