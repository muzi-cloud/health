package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constants.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author mao
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    // 从excel中读取预约设置的数据，批量导入到数据库
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            // 导入到数据库的格式List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<>();
            // 将String[]转换成OrderSetting
            for (String[] strs : list) {
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(strs[0])); // 预约时间
                orderSetting.setNumber(Integer.parseInt(strs[1])); // 可预约人数
                orderSettingList.add(orderSetting);
            }
            // 传递给Service处理
            orderSettingService.addList(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    // 初始化页面当前月的数据信息，返回Result，其中data属性封装的是List<Map>
    @RequestMapping(value = "/getListByOrderDate")
    public Result getListByOrderDate(String date){
        try {
            List<Map<String,Object>> list = orderSettingService.getListByOrderDate(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    // 更新预约设置的可预约人数
    @RequestMapping(value = "/updateNumberByOrderDate")
    public Result updateNumberByOrderDate(@RequestBody Map<String,Object> map){
        try {
            OrderSetting orderSetting = new OrderSetting();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(map.get("currentDate").toString());
            orderSetting.setOrderDate(date);
            orderSetting.setNumber(Integer.parseInt(map.get("number").toString()));
            orderSettingService.updateNumberByOrderDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    //分页查询显示预约列表
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = orderSettingService.findPage(queryPageBean);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }

    //删除预约列表
    @RequestMapping("/delete/{orderId}")
    public Result delete(@PathVariable("orderId")Integer orderId){
        try {
            orderSettingService.delete(orderId);
            return new Result(true,MessageConstant.DELETE_ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.DELETE_ORDER_SUCCESS);
        }

    }
}
