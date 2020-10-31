package com.glc.loginregister.service;

import com.glc.loginregister.entity.*;
import com.glc.loginregister.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Apply> applyfindByOID(int id){
        List<Apply> items = orderMapper.applyfindByOID(id);

        return items;
    }

    public Integer agreeapply(int id) {
        return orderMapper.agreeapply(id);
    }

    public Integer rejectapply(int id) {
        return orderMapper.rejectapply(id);
    }

    public Integer addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    public void updateOrderState(){
        orderMapper.updateOrderState();
    }

}
