package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void addList(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            saveOrUpdateOrderSetting(orderSetting);
        }
    }

    private void saveOrUpdateOrderSetting(OrderSetting orderSetting) {
        // 使用预约时间查询预约设置表，如果数据存在，更新；如果数据不存在，添加
        int count = orderSettingDao.getCountByOrderDate(orderSetting.getOrderDate());
        // 如果数据存在，更新
        if(count>0){
            // 根据预约时间更新可预约人数数量
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }
        // 如果数据不存在，添加
        else{
            // 保存
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map<String, Object>> getListByOrderDate(String date) {
        // 当前月的开始时间
        String beginDate = date+"-"+"1";
        // 当前月的结束时间
        String endDate = date+"-"+"31";
        // 使用范围查询，查询当前月对应的预约设置的数据
        List<OrderSetting> list = orderSettingDao.getListByOrderDateBetween(beginDate,endDate);
        // 封装的结果集是List<Map>
        List<Map<String,Object>> mapList = new ArrayList<>();
        // 遍历list
        for (OrderSetting orderSetting : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            mapList.add(map);
        }
        return mapList;
    }

    // 更新预约设置的可预约人数（更新，也可能新增）
    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {
        saveOrUpdateOrderSetting(orderSetting);
    }
}
