package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constants.RedisConstant;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

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
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealDao setMealDao;

    @Autowired
    JedisPool jedisPool;

    // 保存套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 1：新增套餐，返回套餐id
        setMealDao.add(setmeal);
        // 2：遍历检查组的id，向套餐和检查组的中间表保存数据
        if(checkgroupIds!=null && checkgroupIds.length>0){
            setSetMealAndCheckGroup(checkgroupIds,setmeal.getId());
        }
        //  保存套餐数据（向redis中的key值为setmealPicDbResources添加数据，图片名称）
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    // 分页查询套餐
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealDao.findPage(queryString);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list = setMealDao.findAll();
        return list;
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealCount() {
        return setMealDao.getSetmealCount();
    }

    // 向套餐和检查组的中间表保存数据
    private void setSetMealAndCheckGroup(Integer[] checkgroupIds, Integer setmealId) {
        for (Integer checkgroupId : checkgroupIds) {
            setMealDao.addSetMealAndCheckGroup(setmealId,checkgroupId);
        }
    }
}
