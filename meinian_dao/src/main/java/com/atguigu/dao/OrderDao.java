package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findOrderByCondition(Order order);

    void addOrder(Order order);
}
