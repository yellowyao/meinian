package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

public interface OrderService {

    Result submit(Map<Object, Object> map);

    Result findById(Integer id);
}
