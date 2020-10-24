package com.glc.loginregister.service;

import com.glc.loginregister.entity.Result;
import com.glc.loginregister.entity.User;
import com.glc.loginregister.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 注册
     * @param user
     * @return
     */
    public Result rgister(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User userByName = userMapper.findUserByName(user.getUserName());
            if(userByName!=null){
                //如果查询到 说明存在
                result.setMsg("用户名存在");
            }else {
                userMapper.register(user);
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 登录
     */
    public Result login(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User userByName = userMapper.findUserByName(user.getUserName());
            Long userID = userMapper.login(user);
            if(userByName==null){
                result.setMsg("用户名错误");
            } else if(userID==null){
                result.setMsg("密码错误");
            }else {
                result.setMsg("登录成功");
                result.setSuccess(true);
                user.setUserID(userID);
                User userByID = userMapper.findUserById(userID);
                result.setDetail(userByID);
            }

        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result changePasswd(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long userID = userMapper.login(user);
            if(userID==null){
                result.setMsg("密码错误");
            }else {
                userMapper.ChangePasswd(user);
                result.setMsg("修改成功");
                result.setSuccess(true);
                user.setUserID(userID);
                User userByID = userMapper.findUserById(userID);
                result.setDetail(userByID);
            }

        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result changeInfo(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
                userMapper.ChangeInfo(user);
                result.setMsg("修改成功");
                result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
