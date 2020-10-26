package com.glc.loginregister.mapper;

import com.glc.loginregister.entity.Order;
import com.glc.loginregister.entity.Res;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    @Select("SELECT count(*) from apply where orderID=#{id} and applyState='noanswer'")
    Integer countnoOrder(int id);

    @Select("SELECT COUNT(*)  FROM orders WHERE publishPlace LIKE '%${value}%';")
    Integer countOrderByName(String name);

    @Insert("INSERT INTO orders (orderID,userID,publishTime,timeLimit,publishPlace,orderState,peopleLimit)\n" +
            "    VALUES(#{orderID},#{userID},#{publishTime},#{timeLimit},#{publishPlace},#{orderState},#{peopleLimit});")
    @Options(useGeneratedKeys = true,keyProperty = "orderID",keyColumn = "orderID")
    Integer addOrder(Order order);

    @Select("SELECT * FROM orders where userID=#{id} and (orderState='waiting' or orderState='doing')")
    public List<Order> listNowOrder(int id);

    @Select("SELECT * FROM orders")
    public List<Order> listAllOrders();

    @Update("update orders set orderState ='outdated' where orderstate ='waiting' and timeLimit<TIMESTAMPDIFF(MINUTE,publishTime,now())")
    void updateOrderState();
}
