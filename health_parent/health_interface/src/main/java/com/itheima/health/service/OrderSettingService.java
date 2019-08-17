package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void addList(List<OrderSetting> orderSettingList);

    List<Map<String,Object>> getListByOrderDate(String date);

    void updateNumberByOrderDate(OrderSetting orderSetting);
    //通过分页查询预约列表
    PageResult findPage(QueryPageBean queryPageBean);
    //通过预约id删除对应的id
    void delete(Integer orderId);
}
