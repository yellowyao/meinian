package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;


    @Override
    public void addMuch(ArrayList<OrderSetting> orderSettings) {
        // 1：遍历List<OrderSetting>
        for (OrderSetting orderSetting : orderSettings) {
            // 判断当前的日期之前是否已经被设置过预约日期，使用当前时间作为条件查询数量
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            // 如果设置过预约日期，更新number数量
            if (count > 0) {
                orderSettingDao.edit(orderSetting);
            } else {
                // 如果没有设置过预约日期，执行保存
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public Result initData(String formatDate) {
        try {

            HashMap<String, String> map = new HashMap<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(formatDate.replaceAll("-","/")+"/1"));
            map.put("startDate",formatDate + "-1");
            map.put("endDate",formatDate +"-"+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            List<OrderSetting> orderSettings = orderSettingDao.findBetweenDate(map);
            ArrayList<HashMap> hashMaps = new ArrayList<>();
            for (OrderSetting orderSetting : orderSettings) {
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("date",orderSetting.getOrderDate().getDate());
                objectObjectHashMap.put("number",orderSetting.getNumber());
                objectObjectHashMap.put("reservations",orderSetting.getReservations());
                hashMaps.add(objectObjectHashMap);
            }
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,hashMaps);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);

        }
    }

    @Override
    public Result OrderSet(OrderSetting orderDate) {

        try {
            long count = orderSettingDao.findCountByOrderDate(orderDate.getOrderDate());
            if (count>0){
                orderSettingDao.edit(orderDate);
            }else {
                orderSettingDao.add(orderDate);
            }
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("date",orderDate.getOrderDate().getDate());
            objectObjectHashMap.put("number",orderDate.getNumber());
            objectObjectHashMap.put("reservations",orderDate.getReservations());
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,objectObjectHashMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
