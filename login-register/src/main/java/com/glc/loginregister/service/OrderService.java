package com.glc.loginregister.service;

import com.github.pagehelper.PageHelper;
import com.glc.loginregister.entity.*;
import com.glc.loginregister.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public Integer countnoApply(int id) {
        return orderMapper.countnoOrder(id);
    }

    public List<Order> listNowOrder(int id){
        List<Order> items = orderMapper.listNowOrder(id);

        return items;
    }

    public List<Order> findNowOrder(int id){
        int num = orderMapper.countconapply(id);
        orderMapper.updateOrderPState(id,num);
        List<Order> items = orderMapper.findNowOrder(id);
        return items;
    }

    public List<Order> findHisOrder(int id){
        List<Order> items = orderMapper.findHistoryOrder(id);
        return items;
    }

    public List<Order> findOrder(int id){
        List<Order> items = orderMapper.findOrder(id);
        return items;
    }

    public List<Apply> applyfindByOID(int id){
        List<Apply> items = orderMapper.applyfindByOID(id);

        return items;
    }

    public Integer updateOrderPState(int id,int num){return orderMapper.updateOrderPState(id,num);}

    public Integer agreeapply(int id) {
        int i = orderMapper.agreeapply(id);
        int num = orderMapper.countconapply(id);
        orderMapper.updateOrderPState(id,num);
        return i;
    }

    public Integer rejectapply(int id) {
        return orderMapper.rejectapply(id);
    }

    public Integer addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    public Integer editOrder(int orderID,int timeLimit,int peopleLimit) {
        int i= orderMapper.editOrder(orderID,timeLimit,peopleLimit);
        int num = orderMapper.countconapply(orderID);
        orderMapper.updateOrderPState(orderID,num);
        return i;
    }

    public Integer cancelOrder(int id) {
        return orderMapper.cancelOrder(id);
    }

    public void updateOrderState(){
        orderMapper.updateOrderState();
    }

    public void updateApplyState(){
        orderMapper.updateApplyState();
    }

    public PageBean findOrderByPage(Integer currentPage, int userID,Integer pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数
        PageHelper.startPage(currentPage, pageSize);

        List<Order> allItems = orderMapper.listOrder();
        int countNums = orderMapper.countOrder();            //总记录数
        PageBean<Order> pageBean =new PageBean<>();
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


    public PageBean findOrderByName(String name,int userID,Integer currentPage, Integer pageSize){
        PageHelper.startPage(currentPage, pageSize);
        Map param=new HashMap<>();
        param.put("name",name);
        System.out.println(name);
        List<Order> items = orderMapper.listOrderByName(name,userID);
        int countNums = orderMapper.countOrderByName(name,userID);
        PageBean<Order> pageBean =new PageBean<>();
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

    public Integer countOrder() {
        return orderMapper.countOrder();
    }
}
