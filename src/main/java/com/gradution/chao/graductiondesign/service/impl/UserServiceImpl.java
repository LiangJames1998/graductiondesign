package com.gradution.chao.graductiondesign.service.impl;

import com.gradution.chao.graductiondesign.mapper.UserMapper;
import com.gradution.chao.graductiondesign.pojo.User;
import com.gradution.chao.graductiondesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> getAllUser() {

        List<User> users = userMapper.selectAllUser();

        return users;
    }

    @Override
    public User getUserByPassword(String account) {

        User user = userMapper.selectUserByPassword(account);

        return user;
    }

    @Override
    public void addUserNew(User user) {
        userMapper.addUserNew(user);
    }


    @Override
    public void updateUserInfor(User user) {

        userMapper.updateUserInfo(user);
    }


    @Override
    public User getUserByUID(int u_id) {
        return userMapper.getUserByUID(u_id);
    }


    @Override
    public int findUserMsgDataBrowse(int b_userid) {
        return userMapper.findUserMsgDataBrowse(b_userid);
    }

    @Override
    public int findUserMsgDatablob(int b_userid) {
        return userMapper.findUserMsgDatablob(b_userid);
    }


    @Override
    public int findUserMsgDataComments(String c_username) {
        return userMapper.findUserMsgDataComments(c_username);
    }
}
