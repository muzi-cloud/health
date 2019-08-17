package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/checkitem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;

    // 新增保存（json）
    @RequestMapping(value = "/add")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    // 分页查询
    @RequestMapping(value = "/findPage")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = checkItemService.findPage(queryPageBean.getQueryString(),queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
            return pageResult;
        } catch (Exception e) {
            return null;
        }
    }

    // 删除
    @RequestMapping(value = "/delete")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_DELETE')")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }  catch (RuntimeException e) {
            return new Result(true,e.getMessage());
        }   catch (Exception e) {
            return new Result(true,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    // 主键查询
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }   catch (Exception e) {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    // 编辑保存
    @RequestMapping(value = "/update")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_EDIT')")
    public Result update(@RequestBody CheckItem checkItem){
        try {
            checkItemService.update(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }   catch (Exception e) {
            return new Result(true,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    // 查询所有
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            if(list!=null && list.size()>0){
                return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
            }
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }   catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
