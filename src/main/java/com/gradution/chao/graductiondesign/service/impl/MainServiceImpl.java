package com.gradution.chao.graductiondesign.service.impl;

import com.gradution.chao.graductiondesign.mapper.MainMapper;
import com.gradution.chao.graductiondesign.pojo.*;
import com.gradution.chao.graductiondesign.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mainMapper;

    @Override
    public List<Traveler> findAllTravelinger() {


        return mainMapper.findAllTraveler();
    }

    @Override
    public Traveler findTravelingByTID(int trave_id) {


        return mainMapper.findTravelByID(trave_id);
    }

    @Override
    public List<Blober> findAllBlober() {

        return mainMapper.findAllBlobering();
    }

    @Override
    public Blober findBlobByBID(int blob_id) {

        return mainMapper.findBlobByBID(blob_id);
    }

    @Override
    public List<Comment> findCommentsByBID(int blob_id) {

        return mainMapper.findCommentByBID(blob_id);
    }

    @Override
    public void publishComments(Comment comment) {

        mainMapper.PublishCommentByUID(comment);
    }

    @Override
    public void addArticleComments(int blob_id) {

        mainMapper.addArticleCommentBYBID(blob_id);
    }

    @Override
    public List<Browseblog> findBrowseByUID(int u_id) {

        return mainMapper.findBrowseByUID(u_id);
    }

    @Override
    public void addNewBrowseBy(Browseblog browseblog) {
        mainMapper.addNewBrowseBy(browseblog);
    }

    @Override
    public void deleteBrowseByBID(int b_id) {
        mainMapper.deleteBrowseByBID(b_id);
    }

    @Override
    public void addArticleClicked(int blob_id) {

        mainMapper.addArticleClickedBYBID(blob_id);
    }

    @Override
    public Browseblog findSameBrowse(int b_userid, int b_blogid) {

        return mainMapper.findSameBrowse(b_userid,b_blogid);
    }

    @Override
    public void addTravelClickedBYBID(int t_id) {
        mainMapper.addTravelClickedBYBID(t_id);
    }

    @Override
    public List<Browsetravel> findBrowseTravelByUID(int u_id) {
        return mainMapper.findBrowseTravelByUID(u_id);
    }

    @Override
    public void addNewBrowseTravel(Browsetravel browsetravel) {
        mainMapper.addNewBrowseTravel(browsetravel);
    }

    @Override
    public void deleteBrowseTravel(int b_id) {
        mainMapper.deleteBrowseTravel(b_id);
    }

    @Override
    public Browsetravel findSameBrowseTravel(int b_userid, int b_travelid) {
        return mainMapper.findSameBrowseTravel(b_userid, b_travelid);
    }

    @Override
    public List<Traveler> findFuzzyTravelName(String t_name) {
        return mainMapper.findFuzzyTravelName(t_name);
    }

    @Override
    public List<Blober> findFuzzyBlogTitle(String b_title) {
        return mainMapper.findFuzzyBlogTitle(b_title);
    }

    @Override
    public void UserPublishBlober(Blober blober) {
        mainMapper.UserPublishBlober(blober);
    }

    @Override
    public List<Blober> getUserPublishBlober(int b_author_id) {
        return mainMapper.getUserPublishBlober(b_author_id);
    }

    @Override
    public void deleteBloberByBID(int b_id) {
        mainMapper.deleteBloberByBID(b_id);
    }


    @Override
    public List<Blober> getTuijianBlogsMsg() {
        return mainMapper.getTuijianBlogsMsg();
    }
}
