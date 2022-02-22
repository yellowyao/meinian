package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@Transactional
@Service(interfaceClass =SetmealService.class )
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public Result add(Setmeal setmeal, Integer[] travelgroupIds) {
        try {
            setmealDao.add(setmeal);
            addTravelGroupAndSetmeal(setmeal.getId(),travelgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result edit(Setmeal setmeal, List<Integer> travelgroupIds) {

        return null;
    }

    @Override
    public Result getSetmeal() {
        try {
            List<Setmeal> all = setmealDao.findAll();
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,all);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @Override
    public Result findById(Integer id) {

        try {
            Setmeal setmeal= setmealDao.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    private void addTravelGroupAndSetmeal(Integer SetmealId, Integer[] travelgroupIds){
        if (travelgroupIds != null) {
            for (Integer travelgroupId : travelgroupIds) {
                HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
                stringIntegerHashMap.put("setmealid",SetmealId);
                stringIntegerHashMap.put("travelgroupid",travelgroupId);
                setmealDao.setTravelGroupIdBySetmealId(stringIntegerHashMap);
            }
        }
    }
}
