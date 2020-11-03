package com.glc.loginregister.mapper;

import com.glc.loginregister.entity.Apply;
import com.glc.loginregister.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApplyMapper {

    @Select("SELECT * FROM apply where userID=#{id}")
    public List<Apply> applyfindByID(int id);

    @Select("SELECT * FROM orders where userID!=#{userID} and orderState='waiting'")
    public List<Order> listAvailableOrder(int userID);

    @Update("update apply set applyState='canceled' where applyState='noanswer'")
    public Integer cancelApply(int applyID);

    @Select("select count(*) from apply where userID=#{userID} and orderID=#{orderID}")
    public Integer checkExist(int userID,int orderID);

    @Insert("INSERT INTO apply (applyID,userID,orderID,foodName,applyAmount,applyPlace,applyState)\n" +
            "    VALUES(#{applyID},#{userID},#{orderID},#{foodName},#{applyAmount},#{applyPlace},'waiting');")
    @Options(useGeneratedKeys = true,keyProperty = "applyID",keyColumn = "applyID")
    public Integer addApply(Apply apply);
}
