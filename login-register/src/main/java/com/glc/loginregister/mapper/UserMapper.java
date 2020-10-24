package com.glc.loginregister.mapper;


import com.glc.loginregister.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper
@Repository
public interface UserMapper {


    /**
     *查看用户名是否已经存在
     * @param userName
     * @return
     */
    @Select("select u.userName,u.userPassword from ordinaryuser u where u.userName=#{userName}")
    User findUserByName(@Param("userName") String userName);


    @Select("SELECT * FROM ordinaryuser WHERE userID=#{userID}")
    User findUserById(@Param("userID") Long userID);


    /**
     * 注册
     * @param user
     */
    @Insert("insert into ordinaryuser values(#{userID},#{userName},#{userPassword},#{registerTime},#{userPhonenumber})")
    @Options(useGeneratedKeys = true,keyProperty = "userID",keyColumn = "userID")
    void register(User user);


    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select u.userID from ordinaryuser u where u.userName=#{userName} and userPassword=#{userPassword}")
    Long login(User user);


    @Update("update ordinaryuser set userPassword=#{newpassword} where ordinaryuser.userName=#{userName}")
    void ChangePasswd(User user);

    @Update("update ordinaryuser set userPhonenumber=#{userPhonenumber} where ordinaryuser.userName=#{userName}")
    void ChangeInfo(User user);
}
