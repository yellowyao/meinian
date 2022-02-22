package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    long  findCountByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void edit(OrderSetting orderSetting);
    List<OrderSetting> findBetweenDate(Map map);

    OrderSetting findOrderByOrderDate(String orderDate);
}
