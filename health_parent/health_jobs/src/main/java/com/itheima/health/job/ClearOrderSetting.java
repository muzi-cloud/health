package com.itheima.health.job;


import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ClearOrderSetting {

    @Autowired
    OrderSettingDao orderSettingDao;

    public void clearOrder(){
        System.out.println("执行定时清理数据库任务");
        try {
            String s = DateUtils.parseDate2String(new Date());
            orderSettingDao.clearOrderSetting(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
