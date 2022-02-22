package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public Result add(TravelGroup travelGroup, Integer[] travelItemIds) {
        try{
            travelGroupDao.add(travelGroup);
            Integer id = travelGroup.getId();
            addTravelGroupAndTravelItem(id,travelItemIds);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelGroup> page=travelGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result findById(Integer id) {
        try {
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroupDao.findById(id));
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }

    }

    @Override
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelGroupId(id);
    }

    @Override
    public Result edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        try {
            travelGroupDao.deleteTravelItemIdByTravelGroupId(travelGroup.getId());
            travelGroupDao.edit(travelGroup);
            addTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
            return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }

    }

    @Override
    public Result delete(Integer id) {
        try {
            travelGroupDao.deleteTravelItemIdByTravelGroupId(id);
            travelGroupDao.deleteById (id);

            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }

    }

    @Override
    public Result findAll() {
        List<TravelGroup> all = travelGroupDao.findAll();
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,all);
    }

    private void addTravelGroupAndTravelItem(Integer travelGroupId,Integer[] travelItemIds){
       if (travelItemIds!=null&&travelItemIds.length!=0){
           for (Integer travelItemId : travelItemIds) {
               HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
               stringIntegerHashMap.put("travelGroupId",travelGroupId);
               stringIntegerHashMap.put("travelItemId",travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(stringIntegerHashMap);
           }
       }
    }
}
