package com.atguigu.controller;


import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.util.SMSUtils;
import com.atguigu.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        try {
            String messageCode = "验证码为:" + code;
//            SMSUtils.sendShortMessage(telephone, messageCode);
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 2 * 60, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        try {
            String messageCode = "验证码为:" + code;
//            SMSUtils.sendShortMessage(telephone, messageCode);
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 2 * 60, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
