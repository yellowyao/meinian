package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SetmealDao setmealDao;


    @Override
    public Result submit(Map<Object, Object> map) {

        try {
            String orderDate = (String) map.get("orderDate");
            String telephone = (String) map.get("telephone");
            String idCard = (String) map.get("idCard");
            String name = (String) map.get("name");
            String sex = (String) map.get("sex");
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            OrderSetting orderSetting = orderSettingDao.findOrderByOrderDate(orderDate);
            //检查预约日期是否可预约
            if (orderSetting == null) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            } else if (orderSetting.getNumber() <= orderSetting.getReservations()) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
            //检查是否是会员
            Member member = memberDao.findByPhone(telephone);
            //快速注册
            if (member == null) {
                member=new Member();
                member.setName(name);
                member.setIdCard(idCard);
                member.setSex(sex);
                member.setPhoneNumber(telephone);
                memberDao.addMember(member);
            }
            //查找是否已经预约过
            Order order = new Order();
            order.setOrderDate(orderDate);
            order.setMemberId(member.getId());
            order.setSetmealId(setmealId);
            List<Order> orders = orderDao.findOrderByCondition(order);
            if (orders == null || orders.size() == 0) {
                order.setOrderType(Order.ORDERTYPE_WEIXIN);
                order.setOrderStatus(Order.ORDERSTATUS_NO);
                orderDao.addOrder(order);
                int reservations = orderSetting.getReservations();
                orderSetting.setReservations(reservations+1);
                orderSettingDao.edit(orderSetting);
                return new Result(true, MessageConstant.ORDER_SUCCESS,order);
            } else {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDER_FAIL);
        }

    }

    @Override
    public Result findById(Integer id) {

        try {
            List<Order> orderByCondition = orderDao.findOrderByCondition(new Order(id));
            Order order = orderByCondition.get(0);
            Member member= memberDao.findById(order.getMemberId());
            Setmeal setmeal = setmealDao.findById(order.getSetmealId());
            HashMap<Object, Object> orderInfo = new HashMap<>();
            orderInfo.put("member",member.getName());
            orderInfo.put("setmeal",setmeal.getName());
            orderInfo.put("orderDate",order.getOrderDate());
            orderInfo.put("orderType",order.getOrderType());
            return new Result(true,MessageConstant.ORDER_SUCCESS,orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDER_FAIL);
        }
    }
}
