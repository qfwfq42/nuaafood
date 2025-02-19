package com.glc.loginregister.mapper;

import com.glc.loginregister.entity.Food;
import com.glc.loginregister.entity.Order;
import com.glc.loginregister.entity.Apply;
import com.glc.loginregister.entity.Res;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    @Select("SELECT count(*) from apply where orderID=#{id} and applyState='noanswer'")
    Integer countnoOrder(int id);

    @Select("SELECT * FROM orders")
    public List<Order> listOrder();

    @Select("SELECT * FROM orders WHERE userID=#{userID} and publishPlace LIKE '%${name}%' and " +
            "(orderState='finished' or orderState='canceled' or orderState='outdated') order by publishTime desc")
    public List<Order> listOrderByName(String name,int userID);

    @Select("SELECT COUNT(*)  FROM orders WHERE userID=#{userID} and publishPlace LIKE '%${name}%' and" +
            "(orderState='finished' or orderState='canceled' or orderState='outdated');")
    Integer countOrderByName(String name,int userID);

    @Select("SELECT count(*) from orders")
    Integer countOrder();

    @Insert("INSERT INTO orders (orderID,userID,publishTime,timeLimit,publishPlace,orderState,peopleLimit)\n" +
            "    VALUES(#{orderID},#{userID},#{publishTime},#{timeLimit},#{publishPlace},#{orderState},#{peopleLimit});")
    @Options(useGeneratedKeys = true,keyProperty = "orderID",keyColumn = "orderID")
    Integer addOrder(Order order);

    @Update("update orders set timeLimit=#{timeLimit},peopleLimit=#{peopleLimit} where orderID=#{orderID} and "+
            "orderState='waiting'")
    Integer editOrder(int orderID,int timeLimit,int peopleLimit);

    @Select("SELECT * FROM orders where userID=#{id} and (orderState='waiting' or orderState='doing')")
    public List<Order> listNowOrder(int id);

    @Select("SELECT * FROM orders where orderID=#{id} and (orderState='finished' or orderState='canceled' or orderState='outdated')")
    public List<Order> findHistoryOrder(int id);

    @Select("SELECT * FROM orders where orderID=#{id}")
    public List<Order> findOrder(int id);

    @Select("SELECT * FROM orders where orderID=#{id} and (orderState='waiting' or orderState='doing')")
    public List<Order> findNowOrder(int id);

    @Select("SELECT * FROM orders")
    public List<Order> listAllOrders();

    @Select("SELECT * FROM apply where orderID=#{id}")
    public List<Apply> applyfindByOID(int id);

    @Update("update orders set orderState ='outdated' where orderstate ='waiting' and timeLimit<TIMESTAMPDIFF(MINUTE,publishTime,now())")
    void updateOrderState();

    @Select("SELECT * FROM orders where orderID=#{orderID}")
    public List<Order> orderDetail(int orderID);

    @Select("SELECT COUNT(*) FROM apply WHERE orderID=#{id} and applyState='confirmed'")
    Integer countconapply(int id);



    @Update("update orders set orderState='doing' where orderID=#{id} and peopleLimit<=#{num} and orderState='waiting'")
    Integer updateOrderPState(int id,int num);

    @Update("update apply set applyState='confirmed' where applyID=#{id} and applyState='noanswer'")
    Integer agreeapply(int id);

    @Update("update apply set applyState='rejected' where applyID=#{id} and applyState='noanswer'")
    Integer rejectapply(int id);

    @Update("update orders set orderState='canceled' where orderID=#{id} and orderState='waiting'")
    Integer cancelOrder(int id);

    @Update("update apply,orders set applyState='canceled' where apply.orderID=orders.orderID and (orders.orderState='canceled' or orders.orderState='outdated')")
    Integer updateApplyState();
}
