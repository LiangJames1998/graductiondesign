package com.gradution.chao.graductiondesign.service;

import com.gradution.chao.graductiondesign.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminService {


    Admins getAdmingByMaccount(String m_account);

    /**
     * 查询所有用户信息
     */
    List<User> getAllUserbyAdmin();


    /**
     * 获取所有旅游景点
     * @return
     */
    List<Traveler> getAllTravelerAdmin();

    /**
     * 获取所有博客
     */
    List<Blober> getAllBloberAdmin();

    /**
     * 删除指定ID旅游景点
     */
    void DeleteTravelingByID(int t_id);

    /**
     * 删除指定ID旅游博客
     */
    void DeleteBloberByID(int b_id);


    /**
     * 发布旅游景点信息Traveling
     */
    void addTravelingMsg(Traveler traveler);

    /**
     * 查询有无相同的天气信息
     * @param w_id
     * @return
     */
    WeatherID getWeatherbyWID(int w_id);

    /**
     * 添加城市天气信息
     */
    void addTraveCityWeather(WeatherID weatherID);

    /**
     * 添加景点坐标经纬度
     */
    void addTravelCityPointXY(Point point);

    /**
     * 查询最新的旅游景点信息
     * @return
     */
    int findNewestTravelMsg();


    /**
     * 查询 Mmsg 结果，根据 M_Type
     */
    List<Mmsg> getAdminMmsgByMtype(int m_type);


    /**
     *  根据 M_Type更新 Mmsg 的内容
     */
    void updateMmsgByM_Type(String m_msg,int m_type);

    /**
     *  添加需要进行推荐的旅游 或 博客信息
     */
    void addAdminMmsgByMid(Mmsg mmsg);

    /**
     *   根据 M_Mid 删除 Mmsg数据
     */
    void deleteMmsgByMmid(int m_mid);

    /**
     * 查询排行前列的信息1
     */
    List<Traveler> findMostPopularTravel();

    /**
     * 查询排行前列的信息1
     */
    List<Blober> findMostPopularBlog();


    void updataMmsgAnswerFive(String m_mark,int m_id);

    int findWebSationClicked();

    int findWebSationComments();

    int findWebSationResiger();

    /*
       管理员添加壮瑶民族文化信息nation zhuang-yao
     */
    void addNationZhuangMsg(NationZhuang zhuang);

    void addNationYaoMsg(NationYao yao);

    void updateAdminErInfo(Admins admins);
}
