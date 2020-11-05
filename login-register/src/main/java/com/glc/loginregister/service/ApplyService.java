package com.glc.loginregister.service;

import com.github.pagehelper.PageHelper;
import com.glc.loginregister.entity.Apply;
import com.glc.loginregister.entity.Message;
import com.glc.loginregister.entity.Order;
import com.glc.loginregister.entity.PageBean;
import com.glc.loginregister.mapper.ApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    public List<Apply> applyfindByID(int id){
        List<Apply> items = applyMapper.applyfindByID(id);
        return items;
    }

    public List<Apply> applyfindByAID(int id){
        List<Apply> items = applyMapper.applyfindByAID(id);
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

    public Integer finishApply(int applyID){
        return applyMapper.finishApply(applyID);
    }

    public Integer checkUpdateOrder(int OrderID,int num){
        return applyMapper.checkUpdateOrder(OrderID,num);
    }
    public Integer countFiApply(int OrderID){
        return applyMapper.countFiApply(OrderID);
    }
    public Integer editApply(Apply apply) { return applyMapper.editApply(apply);}

    public PageBean findApplyByPage(Integer currentPage, int userID,Integer pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);

        List<Apply> allItems = applyMapper.listApply();        //全部商品
        int countNums = applyMapper.countApply();            //总记录数
        PageBean<Apply> pageBean =new PageBean<>();
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


    public PageBean findApplyByName(String name,int userID,Integer currentPage, Integer pageSize){
        PageHelper.startPage(currentPage, pageSize);
        Map param=new HashMap<>();
        param.put("name",name);
        System.out.println(name);
        List<Apply> items = applyMapper.listApplyByName(name,userID);
        int countNums = applyMapper.countApplyByName(name,userID);
        PageBean<Apply> pageBean =new PageBean<>();
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
}
