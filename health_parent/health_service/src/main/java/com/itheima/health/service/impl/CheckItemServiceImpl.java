package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        // 传统的做法
        // 至少2条sql，总记录数（select count(*) from t_checkitem）；
        // 当前页显示的记录数（select * from t_checkitem where code = #{value} or name = #{value} limit ?,?）
        // 第一个?表示(currentPage-1)*pageSize
        // 第二个?表示pageSize
        // 使用mybatis的分页插件
        // 1：使用PageHelper初始化当前页
        PageHelper.startPage(currentPage,pageSize);
        // 2：查询、
        List<CheckItem> list = checkItemDao.findCheckItemByCondition(queryString);
        // 3：封装
        PageInfo<CheckItem> pageInfo = new PageInfo<CheckItem>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    // 删除检查项
    @Override
    public void deleteById(Integer id) throws RuntimeException {
        // 删除检查项，之前判断中间表中是否存在数据
        int count = checkItemDao.findCountByCheckItemId(id);
        // 中间表中存在数据，不能删除，抛出异常
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_COUNT_FAIL);
        }
        else{
            // 删除检查项
            checkItemDao.deleteById(id);
        }
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
