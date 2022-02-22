package com.atguigu.dao;

import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void add(TravelGroup travelGroup);
    void setTravelGroupAndTravelItem(Map<String,Integer> map);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);
    void deleteTravelItemIdByTravelGroupId(Integer id);
    void edit(TravelGroup travelGroup);
    void deleteById(Integer id);
    List<TravelGroup> findTravelGroupListBySetmealId(Integer id);
    List<TravelGroup> findAll();
}
