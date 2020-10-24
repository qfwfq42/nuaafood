package com.glc.loginregister.service;

import com.github.pagehelper.PageHelper;
import com.glc.loginregister.entity.Food;
import com.glc.loginregister.entity.PageBean;
import com.glc.loginregister.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodService {
    @Autowired
    private FoodMapper foodMapper;
    public PageBean findFoodByPage(Integer currentPage, Integer pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);

        List<Food> allItems = foodMapper.listFood();        //全部商品
        int countNums = foodMapper.countFood();            //总记录数
        PageBean<Food> pageBean =new PageBean<>();
        pageBean.setItems(allItems);//分页结果
        pageBean.setCurrentPage(currentPage);//当前页
        pageBean.setPageSize(pageSize);//设置每页显示条数
        pageBean.setTotalNum(countNums);//设置总条数


        //计算分页数
        int pageConnt=(countNums+pageSize-1)/pageSize;
        pageBean.setTotalPage(pageConnt);//设置总页数
        if(currentPage<pageConnt){
            pageBean.setIsMore(1);
        }else {
            pageBean.setIsMore(0);
        }
        return pageBean;
    }


    public PageBean findFoodByName(String name,Integer currentPage, Integer pageSize){
        PageHelper.startPage(currentPage, pageSize);
        Map param=new HashMap<>();
        param.put("name",name);
        System.out.println(name);
        List<Food> items = foodMapper.listFoodByName(name);
        int countNums = foodMapper.countFoodByName(name);
        PageBean<Food> pageBean =new PageBean<>();
        pageBean.setItems(items);//分页结果
        pageBean.setCurrentPage(currentPage);//当前页
        pageBean.setPageSize(pageSize);//设置每页显示条数
        pageBean.setTotalNum(countNums);//设置总条数

        //计算分页数
        int pageConnt=(countNums+pageSize-1)/pageSize;
        pageBean.setTotalPage(pageConnt);//设置总页数
        if(currentPage<pageConnt){
            pageBean.setIsMore(1);
        }else {
            pageBean.setIsMore(0);
        }
        return pageBean;
    }

    public Integer countFood() {
        return foodMapper.countFood();
    }
}
