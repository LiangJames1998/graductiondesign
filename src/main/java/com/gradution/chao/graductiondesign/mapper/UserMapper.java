package com.gradution.chao.graductiondesign.mapper;


import com.gradution.chao.graductiondesign.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据库搜索Mapper
 *
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from userdata")
    List<User> selectAllUser();

    @Select("select * from userdata where u_account = #{u_account}")
    User selectUserByPassword(@Param("u_account") String u_account);

    @Insert("insert into userdata(u_account, u_password, u_email ) values(#{u_account}, #{u_password}, #{u_email} )")
    void addUserNew(User user);

    @Update("update userdata set u_password = #{u_password}, u_realname = #{u_realname}, u_autograph = #{u_autograph}, u_headimg = #{u_headimg}, u_tel = #{u_tel}, u_email = #{u_email}, u_hometown = #{u_hometown}, u_birthday = #{u_birthday} where u_id = #{u_id}")
    void updateUserInfo(User user);

    @Select("select * from userdata where u_id = #{u_id}")
    User getUserByUID(@Param("u_id") int u_id);

    @Select("SELECT (select count(*) from browseblog where b_userid = #{b_userid} )+(select count(*) from browsetravel where b_userid = #{b_userid} ) AS SumCount")
    int findUserMsgDataBrowse(@Param("b_userid") int b_userid);

    @Select("select count(*) from blobs where b_author_id = #{b_userid}")
    int findUserMsgDatablob(@Param("b_userid") int b_userid);

    @Select("select count(*) from comments where c_person = #{c_username}")
    int findUserMsgDataComments(@Param("c_username") String c_username);




}
