package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface OrderDao {

    List<Order> findOrderListByCondition(Order order);

    void add(Order order);

    Map findById(Integer id);

    Integer findTodayOrderNumber(String reportDate);

    Integer findTodayVisitsNumber(String reportDate);

    Integer findOrderNumberBetweenOrderDate(@Param(value = "start") String start, @Param(value = "end") String end);

    Integer findVisitsNumberBetweenOrderDate(@Param(value = "start")String start, @Param(value = "end") String end);

    List<Map<String,Object>> findHotSetmeal();
    //通过预约id删除预约
    void delete(Integer orderId);
    //通过id查询预约信息
    Order findById2(Integer orderId);
}
