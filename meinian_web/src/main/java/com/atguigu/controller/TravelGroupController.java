package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        return travelGroupService.add(travelGroup, travelItemIds);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return travelGroupService.findPage(queryPageBean);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        return travelGroupService.findById(id);
    }
    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id){
        return travelGroupService.findTravelItemIdByTravelGroupId(id);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        return travelGroupService.edit(travelGroup, travelItemIds);
    }
    @RequestMapping("/delete")
    public Result delete(Integer id){
        return travelGroupService.delete(id);
    }
    @RequestMapping("findAll")
    public Result findAll(){
        return travelGroupService.findAll();
    }
}
