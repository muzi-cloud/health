package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.CheckItemService;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        //初始化当前页
        PageHelper.startPage(currentPage, pageSize);
        //查询
        List<Menu> list = menuDao.findMenuByCondition(queryString);
        //封装
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    // 删除检查项
    @Override
    public void deleteById(Integer id) throws RuntimeException {
        // 删除检查项，之前判断中间表中是否存在数据
        int count = menuDao.findCountByMenu(id);
        // 中间表中存在数据，不能删除，抛出异常
        if (count > 0) {
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM1_FAIL);
        } else {
            // 删除检查项
            menuDao.deleteById(id);
        }
    }
}
