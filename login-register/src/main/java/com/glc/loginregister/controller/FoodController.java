package com.glc.loginregister.controller;


import com.glc.loginregister.entity.Food;
import com.glc.loginregister.entity.Message;
import com.glc.loginregister.entity.PageBean;
import com.glc.loginregister.entity.User;
import com.glc.loginregister.mapper.ItemMapper;
import com.glc.loginregister.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired(required=false)
    private FoodService foodService;

    /**
     * 分页显示所有数据
     *
     * @param currentPage
     * @param pageSize
     * @return http://localhost:8080/item/findByPage?currentPage=1&pageSize=10
     */
    @RequestMapping("/foodfindByPage")
    @ResponseBody
    public PageBean foodsPage(int currentPage, int pageSize) {
        return foodService.findFoodByPage(currentPage, pageSize);
    }


    /**
     * 根据book_name名称查询记录信息
     *
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/foodfindByPageName")
    @ResponseBody
    public PageBean foodsPageByName(String name, int currentPage, int pageSize) {
        return foodService.findFoodByName(name, currentPage, pageSize);
    }
}
