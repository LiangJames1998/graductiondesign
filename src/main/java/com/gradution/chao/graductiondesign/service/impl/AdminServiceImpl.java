package com.gradution.chao.graductiondesign.service.impl;

import com.gradution.chao.graductiondesign.mapper.AdminMapper;
import com.gradution.chao.graductiondesign.pojo.*;
import com.gradution.chao.graductiondesign.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admins getAdmingByMaccount(String m_account) {
        return adminMapper.getAdmingByMaccount(m_account);
    }

    @Override
    public List<User> getAllUserbyAdmin() {
        return adminMapper.getAllUserbyAdmin();
    }

    @Override
    public List<Traveler> getAllTravelerAdmin() {
        return adminMapper.getAllTravelerAdmin();
    }

    @Override
    public List<Blober> getAllBloberAdmin() {
        return adminMapper.getAllBloberAdmin();
    }

    @Override
    public void DeleteTravelingByID(int t_id) {
        adminMapper.DeleteTravelingByID(t_id);
    }

    @Override
    public void DeleteBloberByID(int b_id) {
        adminMapper.DeleteBloberByID(b_id);
    }

    @Override
    public void addTravelingMsg(Traveler traveler) {
        adminMapper.addTravelingMsg(traveler);
    }

    @Override
    public WeatherID getWeatherbyWID(int w_id) {
        return adminMapper.getWeatherbyWID(w_id);
    }

    @Override
    public void addTraveCityWeather(WeatherID weatherID) {
        adminMapper.addTraveCityWeather(weatherID);
    }

    @Override
    public void addTravelCityPointXY(Point point) {
        adminMapper.addTravelCityPointXY(point);
    }

    @Override
    public int findNewestTravelMsg() {
        return adminMapper.findNewestTravelMsg();
    }

    @Override
    public List<Mmsg> getAdminMmsgByMtype(int m_type) {
        return adminMapper.getAdminMmsgByMtype(m_type);
    }

    @Override
    public void updateMmsgByM_Type(String m_msg,int m_type) {
        adminMapper.updateMmsgByM_Type(m_msg,m_type);
    }

    @Override
    public void addAdminMmsgByMid(Mmsg mmsg) {
        adminMapper.addAdminMmsgByMid(mmsg);
    }

    @Override
    public void deleteMmsgByMmid(int m_mid) {
        adminMapper.deleteMmsgByMmid(m_mid);
    }

    @Override
    public List<Traveler> findMostPopularTravel() {
        return adminMapper.findMostPopularTravel();
    }

    @Override
    public List<Blober> findMostPopularBlog() {
        return adminMapper.findMostPopularBlog();
    }

    @Override
    public void updataMmsgAnswerFive(String m_mark, int m_id) {
        adminMapper.updataMmsgAnswerFive(m_mark,m_id);
    }


    @Override
    public int findWebSationClicked() {
        return adminMapper.findWebSationClicked();
    }

    @Override
    public int findWebSationComments() {
        return adminMapper.findWebSationComments();
    }

    @Override
    public int findWebSationResiger() {
        return adminMapper.findWebSationResiger();
    }


    @Override
    public void addNationZhuangMsg(NationZhuang zhuang) {
        adminMapper.addNationZhuangMsg(zhuang);
    }

    @Override
    public void addNationYaoMsg(NationYao yao) {
        adminMapper.addNationYaoMsg(yao);
    }

    @Override
    public void updateAdminErInfo(Admins admins) {
        adminMapper.updateAdminErInfo(admins);
    }
}
