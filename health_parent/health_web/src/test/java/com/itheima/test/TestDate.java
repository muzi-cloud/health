package com.itheima.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ClassName TestDate
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/12 18:09
 * @Version V1.0
 */
public class TestDate {

    @Test
    public void testDate(){
        List<String> months = new ArrayList<String>();
        // 初始化日期类
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12); // 在当前时间月的基础上-12个月
        for(int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1); // 在12个月之前+1后的月
            months.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        System.out.println(months);
    }
}
