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

    public Integer addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    public void updateOrderState(){
        orderMapper.updateOrderState();
    }

}
