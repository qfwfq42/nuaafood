package com.glc.loginregister.service;

import com.glc.loginregister.entity.Apply;
import com.glc.loginregister.entity.Message;
import com.glc.loginregister.entity.Order;
import com.glc.loginregister.mapper.ApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    public List<Apply> applyfindByID(int id){
        List<Apply> items = applyMapper.applyfindByID(id);
        return items;
    }

    public List<Order> listAvailableOrder(int userID){
        List<Order> items = applyMapper.listAvailableOrder(userID);
        return items;
    }

    public Integer cancelApply(int applyID) {
        return applyMapper.cancelApply(applyID);
    }

    public Integer checkExist(int userID,int orderID) {
        return applyMapper.checkExist(userID,orderID);
    }

    public Integer addApply(Apply apply){
        return applyMapper.addApply(apply);
    }
}
