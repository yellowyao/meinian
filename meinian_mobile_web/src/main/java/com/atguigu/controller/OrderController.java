package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<Object, Object> map) {
        String validateCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        try {
            String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

            if (code==null||!code.equals(validateCode)){
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }else {
                return orderService.submit(map);
            }
        } catch (Exception e) {
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        return orderService.findById(id);
    }

}
