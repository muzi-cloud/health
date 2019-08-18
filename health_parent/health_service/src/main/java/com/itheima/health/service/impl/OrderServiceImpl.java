package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    // 预约设置
    @Autowired
    OrderSettingDao orderSettingDao;

    // 会议Dao
    @Autowired
    MemberDao memberDao;

    // 提交预约
    @Override
    public Result submitOrder(Map map) {
        try {
            // 获取当前体检日期
            String orderDate = (String)map.get("orderDate");
            Date date = DateUtils.parseString2Date(orderDate, "yyyy-MM-dd");
            //  1：使用当前体检日期，查询预约设置表，返回OrderSetting对象
            OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(date);
            //* 如果对象为空，说明预约设置表中没有当前预约日期，此时不能预约，“档期时间不能进行预约”
            if(orderSetting == null){
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            //* 如果对象不为空，比较resersvation和number字段，如果2个值相等或者reservations>=number，说预约已满，不能进行预约
            int number = orderSetting.getNumber(); // 当天最多预约人数
            int reservations = orderSetting.getReservations(); // 已预约人数
            if(reservations>=number){
                return new Result(false, MessageConstant.ORDER_FULL);
            }
            //2：判断当前注册用户是否是会员，使用手机号查询会员表，获取Member的对象
            // 获取手机号
            String telephone = (String)map.get("telephone");
            Member member = memberDao.findMemberByTelephone(telephone);
            /** 如果Member对象不为空，是会员，判断他是否重复预约
        使用会员id、当前预约时间、套餐id查询订单表，如果存在数据，表示已经预约，提示“不能重复预约”
             */
            if(member!=null){
                // 组织查询条件
                Order order = new Order(member.getId(),date,null,null,Integer.parseInt((String)map.get("setmealId")));
                // 查询订单表，判断是否重复预约
                List<Order> list = orderDao.findOrderListByCondition(order);
                if(list!=null && list.size()>0){
                    // 一个会员，一个套餐，一个预约时间不能出现重复，否则就是重复预约
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
            }
            // * 如果Member对象为空，不是会员，注册会员，向会员表中插入数据，并返回会员id
            else{
                member = new Member();
                member.setPhoneNumber((String)map.get("telephone")); // 手机号
                member.setName((String)map.get("name")); // 体检人姓名
                member.setSex((String)map.get("sex"));  // 性别
                member.setIdCard((String)map.get("idCard")); // 身份证
                member.setRegTime(new Date()); // 会议注册时间
                // 插入数据
                memberDao.add(member);
            }
            //3：往订单表中插入数据
            Order order = new Order(member.getId(),date,(String)map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String)map.get("setmealId")));
            orderDao.add(order);
            //4：同时更新预约设置表，使用当前预约时间更新reservations字段+1
            orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSettingDao.updateReservationsByOrderDate(orderSetting);
            return new Result(true, MessageConstant.ORDER_SUCCESS,order); // 页面要使用订单id
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ORDER_FAIL);
        }
    }

    @Override
    public Map findById(Integer id) {
        Map map = orderDao.findById(id);
        if(map.get("orderDate")!=null){
            Date orderDate = (Date)map.get("orderDate");
            String date = null;
            try {
                date = DateUtils.parseDate2String(orderDate,"yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("orderDate",date);
        }
        return map;
    }
}
