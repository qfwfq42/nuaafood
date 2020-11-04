package com.glc.loginregister.mapper;

import com.glc.loginregister.entity.Apply;
import com.glc.loginregister.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApplyMapper {
    @Select("SELECT * FROM apply")
    public List<Apply> listApply();

    @Select("SELECT * FROM apply WHERE userID=#{id} and (applyPlace LIKE '%${name}%' or foodName LIKE '%${name}%') " +
            "and (applyState='finished' or applyState='rejected' or applyState='canceled') order by applyID desc")
    public List<Apply> listApplyByName(String name,int id);

    @Select("SELECT COUNT(*)  FROM apply WHERE userID=#{id} and (applyPlace LIKE '%${name}%' or foodName LIKE '%${name}%') " +
            "and (applyState='finished' or applyState='rejected' or applyState='canceled')")
    Integer countApplyByName(String name,int id);

    @Select("SELECT count(*) from apply")
    Integer countApply();

    @Select("SELECT * FROM apply where userID=#{id} and (applyState='noanswer' or applyState='confirmed') order by applyID desc")
    public List<Apply> applyfindByID(int id);

    @Select("SELECT * FROM apply where applyID=#{id}")
    public List<Apply> applyfindByAID(int id);

    @Select("SELECT * FROM orders where userID!=#{userID} and orderState='waiting' order by publishTime desc")
    public List<Order> listAvailableOrder(int userID);

    @Update("update apply set applyState='canceled' where applyState='noanswer'")
    public Integer cancelApply(int applyID);

    @Select("select count(*) from apply where userID=#{userID} and orderID=#{orderID}")
    public Integer checkExist(int userID,int orderID);

    @Insert("INSERT INTO apply (applyID,userID,orderID,foodName,applyAmount,applyPlace,applyState)\n" +
            "    VALUES(#{applyID},#{userID},#{orderID},#{foodName},#{applyAmount},#{applyPlace},'noanswer');")
    @Options(useGeneratedKeys = true,keyProperty = "applyID",keyColumn = "applyID")
    public Integer addApply(Apply apply);
}
