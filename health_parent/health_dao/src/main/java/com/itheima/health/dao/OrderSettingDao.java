package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface OrderSettingDao {

    void add(OrderSetting orderSetting);

    int getCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getListByOrderDateBetween(@Param(value = "beginDate") String beginDate, @Param(value = "endDate") String endDate);

    OrderSetting findOrderSettingByOrderDate(Date date);

    void updateReservationsByOrderDate(OrderSetting orderSetting);
}
