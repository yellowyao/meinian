package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = TravelItemService.class)
public class TravelItemServiceImpl implements TravelItemService {
    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        PageInfo pageInfo = new PageInfo<>(travelItemDao.findPage(queryPageBean.getQueryString()));
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void deleteById(Integer id) {
        // 在删除自由行之前，先判断自由行的id，在中间表中是否存在数据
        long count = travelItemDao.findCountByTravelItemItemId(id);

        if (count > 0) {
            throw new RuntimeException("不允许删除");
        }
        // 使用自由行的id进行删除
        travelItemDao.deleteById(id);
    }

    @Override
    public TravelItem findById(Integer id) {

        return travelItemDao.findById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public Result findAll() {
        List<TravelItem> travelItems = travelItemDao.findAll();
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItems);
    }


}
