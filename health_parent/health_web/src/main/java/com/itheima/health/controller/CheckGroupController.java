package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
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
@RequestMapping(value = "/checkgroup")
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    // 新增保存（json）
    @RequestMapping(value = "/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    // 分页查询检查组
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.findPage(
                queryPageBean.getQueryString(),
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize());
        return pageResult;
    }

    // 主键查询
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            if(checkGroup!=null){
                return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
            }
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    // 使用检查组的id，查询当前检查组对应检查项的集合
    @RequestMapping(value = "/findCheckItemByCheckGroupId")
    public List<Integer> findCheckItemByCheckGroupId(Integer id){
        List<Integer> list = checkGroupService.findCheckItemByCheckGroupId(id);
        return list;
    }

    // 更新检查组
    @RequestMapping(value = "/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try {
            checkGroupService.update(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    // 查询所有
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

}
