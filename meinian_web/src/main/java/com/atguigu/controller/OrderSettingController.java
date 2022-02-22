package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/OrderSetting")
public class OrderSettingController {

    @Reference
   private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            ArrayList<OrderSetting> orderSettings = new ArrayList<>();
            for (String[] string : strings) {
                OrderSetting orderSetting = new OrderSetting(new Date(string[0]),Integer.parseInt(string[1]));
                orderSettings.add(orderSetting);
            }
            orderSettingService.addMuch(orderSettings);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.UPLOAD_FAIL);
        }
    }

    @RequestMapping("/initData")
    public Result initData(String formatDate){
        return orderSettingService.initData(formatDate);
    }

    @RequestMapping("/OrderSet")
    public Result OrderSet(@RequestBody OrderSetting orderSetting){
        return orderSettingService.OrderSet(orderSetting);
    }

}
