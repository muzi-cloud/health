package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() {

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            // 算当前时间
            String reportDate = DateUtils.parseDate2String(new Date());
            // 根据当前时间，计算当前时间的本周周一的日期
            String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            // 根据当前时间，计算当前时间的本周周日的日期
            String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            // 根据当前时间，计算当前时间的本月第1天的日期
            String monthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            // 根据当前时间，计算当前时间的本月最后1天的日期
            String monthLasyDay = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());



            // 当天新增会员数
            Integer todayNewMember = memberDao.findTodayNewMember(reportDate);
            // 总会员数
            Integer totalMember = memberDao.findTotalMember();
            // 本周新增会员数
            Integer thisWeekNewMember =  memberDao.findNewMemberAfterRegTime(weekMonday);
            // 本月新增会员数
            Integer thisMonthNewMember =  memberDao.findNewMemberAfterRegTime(monthFirstDay);

            // 今日预约数
            Integer todayOrderNumber = orderDao.findTodayOrderNumber(reportDate);
            // 今日到诊数
            Integer todayVisitsNumber = orderDao.findTodayVisitsNumber(reportDate);
            // 本周预约数
            Integer thisWeekOrderNumber = orderDao.findOrderNumberBetweenOrderDate(weekMonday,weekSunday);
            // 本月预约数
            Integer thisMonthOrderNumber = orderDao.findOrderNumberBetweenOrderDate(monthFirstDay,monthLasyDay);
            // 本周到诊数
            Integer thisWeekVisitsNumber = orderDao.findVisitsNumberBetweenOrderDate(weekMonday,weekSunday);
            // 本月到诊数
            Integer thisMonthVisitsNumber = orderDao.findVisitsNumberBetweenOrderDate(monthFirstDay,monthLasyDay);
            // 热门套餐
            List<Map<String,Object>> hotSetmealList = orderDao.findHotSetmeal();

            // 当前时间
            map.put("reportDate",reportDate);
            // 当天新增会员数
            map.put("todayNewMember",todayNewMember);
            // 总会员数
            map.put("totalMember",totalMember);
            // 本周新增会员数
            map.put("thisWeekNewMember",thisWeekNewMember);
            // 本月新增会员数
            map.put("thisMonthNewMember",thisMonthNewMember);
            // 今日预约数
            map.put("todayOrderNumber",todayOrderNumber);
            // 今日到诊数
            map.put("todayVisitsNumber",todayVisitsNumber);
            // 本周预约数
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            // 本周到诊数
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            // 本月预约数
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            // 本月到诊数
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            // 热门套餐
            map.put("hotSetmeal",hotSetmealList);


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("运行错误");
        }
        return map;
    }
}
