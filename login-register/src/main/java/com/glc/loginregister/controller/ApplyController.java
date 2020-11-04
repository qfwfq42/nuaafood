package com.glc.loginregister.controller;

import com.glc.loginregister.entity.Apply;
import com.glc.loginregister.entity.Message;
import com.glc.loginregister.entity.Order;
import com.glc.loginregister.entity.PageBean;
import com.glc.loginregister.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired(required=false)
    private ApplyService applyService;

    @RequestMapping("/applyfindByID")
    @ResponseBody
    public List<Apply> applyfindByID(int userID){
        return applyService.applyfindByID(userID);
    }

    @RequestMapping("/applyfindByAID")
    @ResponseBody
    public List<Apply> applyfindByAID(int applyID){
        return applyService.applyfindByAID(applyID);
    }

    @RequestMapping("/listAvailableOrder")
    @ResponseBody
    public List<Order> listAvailableOrder(int userID) {
        return applyService.listAvailableOrder(userID);
    }

    @RequestMapping("/cancelApply")
    @ResponseBody
    public Message cancelApply(int applyID){
        Integer integer = applyService.cancelApply(applyID);
        Message message=new Message();
        if(integer>=1){
            message.setInfo("取消成功");
            return message;
        }else {
            message.setInfo("取消失败");
            return message;
        }
    }

    @RequestMapping("/addApply")
    @ResponseBody
    public Message addApply(Apply apply){
        Integer flag = applyService.checkExist(apply.getUserID(),apply.getOrderID());
        Message message=new Message();
        if(flag>=1){
            message.setInfo("已申请");
            return message;
        }
        Integer integer = applyService.addApply(apply);

        if(integer>=1){
            message.setInfo("申请成功");
            return message;
        }else {
            message.setInfo("状态已过期，请重试");
            return message;
        }
    }

    @RequestMapping("/applyfindByPage")
    @ResponseBody
    public PageBean foodsPage(int currentPage, int userID,int pageSize) {
        return applyService.findApplyByPage(currentPage, userID , pageSize);
    }

    @RequestMapping("/applyfindByPageName")
    @ResponseBody
    public PageBean foodsPageByName(String name, int userID,int currentPage, int pageSize) {
        return applyService.findApplyByName(name, userID ,currentPage, pageSize);
    }
}
