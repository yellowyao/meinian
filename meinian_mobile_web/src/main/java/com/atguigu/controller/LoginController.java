package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (redisCode==null||!redisCode.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else {
          Member member= memberService.findByTelephone(telephone);
          if (member==null){
              member=new Member();
              member.setPhoneNumber(telephone);
              member.setRegTime(new Date());
              memberService.add(member);
          }
            Cookie login_member_telephone = new Cookie("login_member_telephone", telephone);
            login_member_telephone.setPath("/");
            login_member_telephone.setMaxAge(60*60*24*60);
            response.addCookie(login_member_telephone);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }
    }
}
