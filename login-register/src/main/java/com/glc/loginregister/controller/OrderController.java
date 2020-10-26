package com.glc.loginregister.controller;

import com.glc.loginregister.entity.Item;
import com.glc.loginregister.entity.Message;
import com.glc.loginregister.entity.Order;
import com.glc.loginregister.entity.Res;
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

    @RequestMapping("/orderfindByID")
    @ResponseBody
    public List<Order> orderfindByID(int userID){
        return orderService.listNowOrder(userID);
    }

    @RequestMapping("/countnoapply")
    @ResponseBody
    public Message countNoApply(int orderID){
        Integer integer = orderService.countnoApply(orderID);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("存在");
            System.out.println("存在");
            return message;
        }else {
            message.setInfo("不存在");
            System.out.println("不存在");
            return message;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

}
