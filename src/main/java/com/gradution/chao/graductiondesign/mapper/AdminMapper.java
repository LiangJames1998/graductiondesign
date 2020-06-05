package com.gradution.chao.graductiondesign.mapper;


import com.gradution.chao.graductiondesign.pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    /**
     * 查询指定用户
     * @param m_account
     * @return
     */
    @Select("select * from madmin where m_account = #{m_account}")
    Admins getAdmingByMaccount(@Param("m_account") String m_account);

    /**
     * 查询所有用户信息
     */
    @Select("select * from userdata")
    List<User> getAllUserbyAdmin();


    /**
     * 获取所有旅游景点
     * @return
     */
    @Select("select * from traveling")
    List<Traveler> getAllTravelerAdmin();

    /**
     * 获取所有博客
     */
    @Select("select * from blobs")
    List<Blober> getAllBloberAdmin();

    /**
     * 删除指定ID旅游景点
     */
    @Delete("delete from traveling where t_id = #{t_id}")
    void DeleteTravelingByID(@Param("t_id") int t_id);

    /**
     * 删除指定ID旅游博客
     */
    @Delete("delete from blobs where b_id = #{b_id}")
    void DeleteBloberByID(@Param("b_id") int b_id);

    /**
     * 发布旅游景点信息Traveling
     */
    @Insert("insert into traveling(t_name, t_city, t_text1, t_text2, t_text3, t_text4, t_uptime, t_picture1, t_picture2, t_picture3, t_picture4, t_picture5, t_picture6) values(#{t_name}, #{t_city}, #{t_text1}, #{t_text2}, #{t_text3}, #{t_text4}, #{t_uptime}, #{t_picture1}, #{t_picture2}, #{t_picture3}, #{t_picture4}, #{t_picture5}, #{t_picture6})")
    void addTravelingMsg(Traveler traveler);

    /**
     * 查询有无相同的天气信息
     * @param w_id
     * @return
     */
    @Select("select * from weather where w_id = #{w_id} limit 1")
    WeatherID getWeatherbyWID(@Param("w_id") int w_id);

    /**
     * 查询最新的旅游景点ID值
     * @return
     */
    @Select("select max(t_id) from traveling")
    int findNewestTravelMsg();

    /**
     * 添加城市天气信息
     */
    @Insert("insert into weather(w_id, w_city) values(#{w_id}, #{w_city})")
    void addTraveCityWeather(WeatherID weatherID);

    /**
     * 添加景点坐标经纬度
     */
    @Insert("insert into point(p_trid,p_x,p_y) values(#{p_trid},#{p_x},#{p_y})")
    void addTravelCityPointXY(Point point);


    /**
     * 查询 Mmsg 结果，根据 M_Type
     */
    @Select("select * from mmsg where m_type = #{m_type}")
    List<Mmsg> getAdminMmsgByMtype(@Param("m_type") int m_type);


    /**
     *  根据 M_Type更新 Mmsg 的内容
     */
    @Update("update mmsg set m_msg = #{m_msg} where m_type = #{m_type}")
    void updateMmsgByM_Type(@Param("m_msg") String m_msg,@Param("m_type") int m_type);

    /**
     *  添加需要进行推荐的旅游 或 博客信息
     */
    @Insert("insert into mmsg(m_type,m_msg,m_mid,m_title,m_clicked,m_mark) values(#{m_type},#{m_msg},#{m_mid},#{m_title},#{m_clicked},#{m_mark})")
    void addAdminMmsgByMid(Mmsg mmsg);

    /**
     *   根据 M_Mid 删除 Mmsg数据
     */
    @Delete("delete from mmsg where m_mid = #{m_mid}")
    void deleteMmsgByMmid(@Param("m_mid") int m_mid);


    /**
     * 查询排行前列的信息1
     */
    @Select("select * from traveling order by t_clicked desc limit 2")
    List<Traveler> findMostPopularTravel();

    /**
     * 查询排行前列的信息1
     */
    @Select("select * from blobs order by b_clicked desc limit 2")
    List<Blober> findMostPopularBlog();

    @Update("update mmsg set m_mark = #{m_mark} where m_id = #{m_id}")
    void updataMmsgAnswerFive(@Param("m_mark") String m_mark,@Param("m_id") int m_id);


    /*
      查询网站浏览统计信息
     */
    @Select(" SELECT a.b_clicked + b.snum2 + c.snum3   + d.snum4  FROM ( SELECT SUM(b_clicked)b_clicked from blobs)a, ( SELECT SUM(t_clicked)snum2 from traveling )b ,(SELECT SUM(n_clicked)snum3 FROM nation_y )c , (SELECT SUM(n_clicked)snum4 FROM nation_z )d")
    int findWebSationClicked();


    @Select("select count(*) from comments")
    int findWebSationComments();

    @Select("select count(*) from userdata")
    int findWebSationResiger();

    /*
       管理员添加壮瑶民族文化信息nation zhuang-yao
     */
    @Insert("insert into nation_z(n_title, n_time, n_content1, n_content2, n_content3, n_picture1, n_picture2, n_picture3) values(#{n_title}, #{n_time}, #{n_content1}, #{n_content2}, #{n_content3}, #{n_picture1}, #{n_picture2}, #{n_picture3} ) ")
    void addNationZhuangMsg(NationZhuang zhuang);

    @Insert("insert into nation_y(n_title, n_time, n_content1, n_content2, n_content3, n_picture1, n_picture2, n_picture3) values(#{n_title}, #{n_time}, #{n_content1}, #{n_content2}, #{n_content3}, #{n_picture1}, #{n_picture2}, #{n_picture3} ) ")
    void addNationYaoMsg(NationYao yao);

    /**
     * 更改管理员密码头像昵称信息
     */
    @Update("update madmin set m_password = #{m_password}, m_headimg = #{m_headimg},  m_realname = #{m_realname} where m_id = #{m_id}")
    void updateAdminErInfo(Admins admins);


}
