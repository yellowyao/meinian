package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;

import java.util.ArrayList;

public interface OrderSettingService {


    void addMuch(ArrayList<OrderSetting> orderSettings);

    Result initData(String formatDate);

    Result OrderSet(OrderSetting orderDate);
}
