package com.glc.loginregister.mapper;

import com.glc.loginregister.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FoodMapper {
    /**
     * 查询所有记录
     * @return
     */
    @Select("SELECT * FROM food")
    public List<Food> listFood();

    /**
     * 查询总数
     * @return
     */
    @Select("SELECT count(*) from food")
    Integer countFood();


    @Select("SELECT * FROM food WHERE foodName LIKE '%${value}%'")
    public List<Food> listFoodByName(String name);


    /**
     * 模糊查询的总记录数
     * @return
     */
    @Select("SELECT COUNT(*)  FROM food WHERE foodName LIKE '%${value}%';")
    Integer countFoodByName(String name);
}
