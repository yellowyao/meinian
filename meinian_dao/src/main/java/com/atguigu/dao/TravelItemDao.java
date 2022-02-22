package com.atguigu.dao;

import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);
    List<TravelItem> findPage(String queryString);
    void deleteById(Integer id);
    long findCountByTravelItemItemId(Integer id);
    TravelItem findById(Integer id);
    void edit(TravelItem travelItem);
    List<TravelItem> findAll();
    List<TravelItem> findTravelItemListByTravelGroupId(Integer id);
}
