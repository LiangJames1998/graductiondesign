package com.gradution.chao.graductiondesign.service;

import com.gradution.chao.graductiondesign.pojo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 实际旅游景点推荐服务类
 */
public interface MainService {


    //查询所有的旅游景点信息
    List<Traveler> findAllTravelinger();

    //根据t_id查询对应的景点信息
    Traveler findTravelingByTID(int trave_id);

    //查询所有的博客文章信息
    List<Blober> findAllBlober();

    //根据b_id查询对应的文章信息
    Blober findBlobByBID(int blob_id);

    //根据文章ID查询出其所有的评论
    List<Comment> findCommentsByBID(int blob_id);

    //用户对一篇文章发表评论
    void publishComments(Comment comment);

    //对旅游文章ID的评论量加一
    void addArticleComments(int blob_id);

    //对旅游文章的点击量加一
    void addArticleClicked(int blob_id);

    //对景区的点击量加一
    void addTravelClickedBYBID(int t_id);

    //查询浏览记录（根据用户ID查询）
    List<Browseblog> findBrowseByUID(int u_id);

    //添加浏览记录(根据用户ID)
    void addNewBrowseBy(Browseblog browseblog);

    //删除浏览记录
    void deleteBrowseByBID(int b_id);

    //查询是否有相同的浏览记录
    Browseblog findSameBrowse(int b_userid, int b_blogid);

    /**
     *  对景点景区浏览记录的操作
     */
    List<Browsetravel> findBrowseTravelByUID(int u_id);

    void addNewBrowseTravel(Browsetravel browsetravel);

    void deleteBrowseTravel(int b_id);

    Browsetravel findSameBrowseTravel(int b_userid,int b_travelid);

    /**
     * 用户模糊查询操作
     *
     */
    //模糊查询景区名称
    List<Traveler> findFuzzyTravelName(String t_name);

    //模糊查询博客名称
    List<Blober> findFuzzyBlogTitle(String b_title);


    /**
     * 用户发布博客文章
     */
    void UserPublishBlober(Blober blober);



    /**
     * 查询当前用户发布的博客文章
     *
     */
    List<Blober> getUserPublishBlober(int b_author_id);


    /**
     * 删除指定 b_id 的博客文章信息
     */
    void deleteBloberByBID(int b_id);


    List<Blober> getTuijianBlogsMsg();
}
