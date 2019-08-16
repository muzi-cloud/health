package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 1：保存检查组
        checkGroupDao.add(checkGroup);
        // 2：使用保存后的检查组的id和检查项的id，向中间表中插入数据
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    // 使用检查组的id，查询当前检查组对应检查项的集合
    @Override
    public List<Integer> findCheckItemByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemByCheckGroupId(id);
    }

    // 更新检查组
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 1：更新检查组（update语句）
        checkGroupDao.update(checkGroup);
        // 2：使用检查组id，删除检查组和检查项的中间表
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckGroupId(checkGroup.getId());
        // 3：在重新建立关联关系（已经做过了）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    // 向检查组检查项中间表中插入数据
    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if(checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkItemId : checkitemIds) {
                // 方案一：传递多个参数，在Dao中使用@Param指定每个参数名称
                //checkGroupDao.addCheckGroupAndCheckItem(checkGroupId,checkItemId);
                // 方案二：传递一个Map<String,Integer>，直接使用key指定名称
                Map<String,Integer> map = new HashMap<String,Integer>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkItemId",checkItemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }
    }
}
