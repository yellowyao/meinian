package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {

    Result add(TravelGroup travelGroup,Integer[] travelItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Result findById(Integer id);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    Result edit(TravelGroup travelGroup, Integer[] travelItemIds);

    Result delete(Integer id);

    Result findAll();

}
