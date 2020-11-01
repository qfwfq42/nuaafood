package com.glc.loginregister.controller;

import com.glc.loginregister.entity.*;
import com.glc.loginregister.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired(required=false)
    private OrderService orderService;

    /*
    @RequestMapping("/countpeople")
    @ResponseBody
    public Res countpeople(int id){
        return orderService.countnoApply(id);
    }
    */
    @RequestMapping("/addOrder")
    @ResponseBody
    public Message insertOrder(Order order){
        Integer integer = orderService.addOrder(order);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("添加成功");
            return message;
        }else {
            message.setInfo("添加失败");
            return message;
        }
    }

    @RequestMapping("/editOrder")
    @ResponseBody
    public Message editOrder(int orderID,int timeLimit,int peopleLimit){
        Integer integer = orderService.editOrder(orderID,timeLimit,peopleLimit);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("修改成功");
            orderService.updateOrderState();
            orderService.updateOrderPState(orderID,peopleLimit);
            return message;
        }else {
            message.setInfo("修改失败");
            return message;
        }
    }

    @RequestMapping("/orderfindByID")
    @ResponseBody
    public List<Order> orderfindByID(int userID){
        return orderService.listNowOrder(userID);
    }

    @RequestMapping("/orderfindByOID")
    @ResponseBody
    public List<Order> orderfindByOID(int orderID){
        return orderService.findNowOrder(orderID);
    }

    @RequestMapping("/findHisOrder")
    @ResponseBody
    public List<Order> findHisOrder(int orderID){
        return orderService.findHisOrder(orderID);
    }

    @RequestMapping("/applyfindByOID")
    @ResponseBody
    public List<Apply> applyfindByOID(int orderID){
        return orderService.applyfindByOID(orderID);
    }

    @RequestMapping("/countnoapply")
    @ResponseBody
    public Message countNoApply(int orderID){
        Integer integer = orderService.countnoApply(orderID);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("存在");
            return message;
        }else {
            message.setInfo("不存在");
            return message;
        }
    }

    @RequestMapping("/agreeapply")
    @ResponseBody
    public Message agreeapply(int applyID){
        Integer integer = orderService.agreeapply(applyID);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("回复成功");
            return message;
        }else {
            message.setInfo("信息有误，请刷新");
            return message;
        }
    }

    @RequestMapping("/rejectapply")
    @ResponseBody
    public Message rejectapply(int applyID){
        Integer integer = orderService.rejectapply(applyID);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("拒绝成功");
            return message;
        }else {
            message.setInfo("信息有误，请刷新");
            return message;
        }
    }

    @RequestMapping("/cancelOrder")
    @ResponseBody
    public Message cancelOrder(int orderID){
        Integer integer = orderService.cancelOrder(orderID);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("取消成功");
            orderService.updateApplyState();
            return message;
        }else {
            message.setInfo("信息有误，请刷新");
            return message;
        }
    }

    @RequestMapping("/orderfindByPage")
    @ResponseBody
    public PageBean foodsPage(int currentPage, int pageSize) {
        return orderService.findOrderByPage(currentPage, pageSize);
    }

    @RequestMapping("/orderfindByPageName")
    @ResponseBody
    public PageBean foodsPageByName(String name, int currentPage, int pageSize) {
        return orderService.findOrderByName(name, currentPage, pageSize);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

}
