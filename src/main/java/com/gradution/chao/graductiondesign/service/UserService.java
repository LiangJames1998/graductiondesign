package com.gradution.chao.graductiondesign.service;


import com.gradution.chao.graductiondesign.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户服务类
 */
public interface UserService {

    //获取所有用户列表
    List<User> getAllUser();

    //根据密码查询用户
    User getUserByPassword(String account);

    //插入新用户
    void addUserNew(User user);

    //更改用户信息
    void updateUserInfor(User user);

    User getUserByUID(int u_id);


    int findUserMsgDataBrowse(int b_userid);

    int findUserMsgDatablob(int b_userid);

    int findUserMsgDataComments(String c_username);

}
