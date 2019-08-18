package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.CheckItemService;
import com.itheima.health.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Reference
    MenuService menuService;



    // 分页查询
    @RequestMapping(value = "/findPage")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = menuService.findPage(queryPageBean.getQueryString(),queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
            return pageResult;
        } catch (Exception e) {
            return null;
        }
    }

    // 新增保存（json）
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return new Result(true, MessageConstant.ADD_MEMBER1_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEMBER1_FAIL);
        }
    }

    // 主键查询(编辑回显)
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            Menu menu = menuService.findById(id);
            return new Result(true,MessageConstant.EDIT_CHECKITEM1_SUCCESS,menu);
        }   catch (Exception e) {
            return new Result(true,MessageConstant.EDIT_CHECKITEM1_FAIL);
        }
    }

    // 编辑保存
    @RequestMapping(value = "/update")
    public Result update(@RequestBody Menu menu){
        try {
            menuService.update(menu);
            return new Result(true,MessageConstant.EDIT_CHECKITEM2_SUCCESS);
        }   catch (Exception e) {
            return new Result(true,MessageConstant.EDIT_CHECKITEM2_FAIL);
        }
    }


    // 删除
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            menuService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM1_SUCCESS);
        }  catch (RuntimeException e) {
            return new Result(true,e.getMessage());
        }   catch (Exception e) {
            return new Result(true,MessageConstant.DELETE_CHECKITEM1_FAIL);
        }
    }

}
