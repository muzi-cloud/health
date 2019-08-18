package com.itheima.health.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用来封装预约列表数据
 */
public class OrderList implements Serializable {
    private Integer orderId;//预约Id
    private String name;//会员名
    private Integer sex;//会员性别
    private String phoneNumber;//会员电话
    private Date orderDate;//预约时间
    private String setmealName;//预约套餐

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }
}
