package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    Result add(Setmeal setmeal, Integer[] travelgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Result edit(Setmeal setmeal, List<Integer> travelgroupIds);

    Result getSetmeal();

    Result findById(Integer id);
}
