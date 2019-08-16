package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetMealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/report")
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    SetMealService setMealService;

    @Reference
    ReportService reportService;

    // 统计图形报表，查询会员每月注册的人数
    @RequestMapping(value = "/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            List<String> months = new ArrayList<String>();
            // 初始化日期类
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12); // 在当前时间月的基础上-12个月
            for(int i=0;i<12;i++){
                calendar.add(Calendar.MONTH,1); // 在12个月之前+1后的月
                months.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            // 传递月份的集合，根据集合中的月份查询当前月的会员注册数量
            List<Integer> memberCount = memberService.findMemberCountByRegTime(months);
            map.put("months",months);  // 月份（[2018-09, 2018-10, 2018-11, 2018-12, 2019-01, 2019-02, 2019-03, 2019-04, 2019-05, 2019-06, 2019-07, 2019-08]）
            map.put("memberCount",memberCount);  // 会员数量
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    // 统计图形报表，套餐预约占比饼形图统计
    @RequestMapping(value = "/getSetmealReport")
    public Result getSetmealReport(){
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            List<Map<String,Object>> setmealCount = setMealService.getSetmealCount();
            List<String> setmealNames = new ArrayList<String>();
            // 遍历setmealCount
            for (Map<String, Object> stringObjectMap : setmealCount) {
                String name = (String)stringObjectMap.get("name");
                setmealNames.add(name);
            }
            map.put("setmealNames",setmealNames);
            map.put("setmealCount",setmealCount);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    // 统计报表
    @RequestMapping(value = "/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            // 获取数据
            Map<String,Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    // 统计报表（Excel导出）
    @RequestMapping(value = "/exportBusinessReport")
    public String exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            // 获取数据(excel报表数据)
            Map<String,Object> map = reportService.getBusinessReportData();
            // 1：读取项目中报表模板的路径位置
            String realPath = request.getSession().getServletContext().getRealPath("template" + File.separator + "report_template.xlsx");
            // 2：使用POI报表加载模板文件
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(realPath)));
            // 3：组织数据，并把数据填充到POI报表指定的位置（POI报表api， XSSFWorkbook（工作簿）；XSSFSheet（工作表）；XSSFRow（行）；XSSFCell（单元格）
            // (1)获取sheet
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);// 第1个sheet
            // (2)获取row
            XSSFRow row2 = sheet.getRow(2);//2表示第3行
            // (3)找到对应的cell，赋值
            // 5表示第6个单元格
            row2.getCell(5).setCellValue((String)map.get("reportDate"));

            XSSFRow row4 = sheet.getRow(4);
            row4.getCell(5).setCellValue((Integer)map.get("todayNewMember"));
            row4.getCell(7).setCellValue((Integer)map.get("totalMember"));


            /**组织其他数据*/
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");

            XSSFRow row = null;
            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map1 : hotSetmeal){//热门套餐
                String name = (String) map1.get("name");
                Long setmeal_count = (Long) map1.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map1.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            // 4：将POI报表以IO流的形式，输出到Response中
            ServletOutputStream outputStream = response.getOutputStream();
            // 【导出】
            //(1)指定输出类型（excel类型）
            response.setContentType("application/vnd.ms-excel");
            //(2)指定下载方式（附件(attachment;filename=export.xlsx)、内连(inline)）
            response.setHeader("Content-Disposition","attachment;filename=export.xlsx");
            xssfWorkbook.write(outputStream);

            outputStream.flush();
            outputStream.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据年龄间隔查询对应的人数
    @RequestMapping("/getMemberReportByAge")
    public Result getMemberReportByAge(){
        try {
            Map<String,Object> map=memberService.findMemberReportByAge();
            return new Result(true, MessageConstant.GET_AGE_LIST_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_AGE_LIST_FAIL);
        }
    }
}
