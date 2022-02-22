package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);
    void setTravelGroupIdBySetmealId(Map<String,Integer> map);
    Page<Setmeal> findPage(String queryString);

    List<Integer> findTravelGroupIdBySetmealId(Integer id);
    void deleteTravelGroupIdBySetmealId(Integer id);
    void edit(Setmeal setmeal);
    void deleteById(Integer id);
    List<Setmeal> findAll();
    Setmeal findById(Integer id);
}
