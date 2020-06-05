package com.gradution.chao.graductiondesign.mapper;


import com.gradution.chao.graductiondesign.pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainMapper {

    @Select("select * from traveling")
    List<Traveler> findAllTraveler();


    @Select("select * from traveling where t_id = #{trave_id}")
    Traveler findTravelByID(@Param("trave_id") int trave_id);

    @Select("select * from blobs")
    List<Blober> findAllBlobering();

    @Select("select * from blobs where b_id = #{blob_id}")
    Blober findBlobByBID(@Param("blob_id") int blob_id);

    @Select("select * from comments where c_articleid = #{blob_id}")
    List<Comment> findCommentByBID(@Param("blob_id") int blob_id);

    @Insert("insert into comments(c_articleid, c_person, c_person_img,c_time,c_text) values(#{c_articleid}, #{c_person}, #{c_person_img}, #{c_time}, #{c_text})")
    void PublishCommentByUID(Comment comment);

    @Update("update blobs set b_comment = b_comment + 1 where b_id = #{blob_id}")
    void addArticleCommentBYBID(@Param("blob_id") int blob_id);
    //博客点击量加一
    @Update("update blobs set b_clicked = b_clicked + 1 where b_id = #{blob_id}")
    void addArticleClickedBYBID(@Param("blob_id") int blob_id);
    //景区点击量加一
    @Update("update traveling set t_clicked = t_clicked + 1 where t_id = #{t_id}")
    void addTravelClickedBYBID(@Param("t_id") int t_id);

    /**
     *   对博客浏览记录的操作
     */
    //查询浏览记录（根据用户ID查询）
    @Select("select * from browseblog where b_userid = #{u_id}")
    List<Browseblog> findBrowseByUID(@Param("u_id") int u_id);

    //添加浏览记录(根据用户ID)
    @Insert("insert into browseblog(b_userid, b_blogid, b_time ) values(#{b_userid}, #{b_blogid},#{b_time})")
    void addNewBrowseBy(Browseblog browseblog);

    //删除浏览记录
    @Delete("delete from browseblog where b_id = #{b_id}")
    void deleteBrowseByBID(@Param("b_id") int b_id);

    //查询是否有相同的记录
    @Select("select * from browseblog where b_userid = #{b_userid} and b_blogid = #{b_blogid}")
    Browseblog findSameBrowse(@Param("b_userid") int b_userid, @Param("b_blogid") int b_blogid);


    /**
     *  对景点景区浏览记录的操作
     *
     */
    @Select("select * from browsetravel where b_userid = #{u_id}")
    List<Browsetravel> findBrowseTravelByUID(@Param("u_id") int u_id);

    @Insert("insert into browsetravel(b_userid, b_travelid, b_time ) values(#{b_userid}, #{b_travelid}, #{b_time})")
    void addNewBrowseTravel(Browsetravel browsetravel);

    @Delete("delete from browsetravel where b_id = #{b_id}")
    void deleteBrowseTravel(@Param("b_id") int b_id);

    @Select("select * from browsetravel where b_userid = #{b_userid} and b_travelid = #{b_blogid}")
    Browsetravel findSameBrowseTravel(@Param("b_userid") int b_userid, @Param("b_blogid") int b_travelid);

    /**
     * 用户模糊查询操作
     *
     */
    //模糊查询景区名称
    @Select("select * from traveling where t_name like #{t_name}")
    List<Traveler> findFuzzyTravelName(@Param("t_name") String t_name);

    //模糊查询博客名称
    @Select("select * from blobs where b_title like #{b_title}")
    List<Blober> findFuzzyBlogTitle(@Param("b_title") String b_title);


    /**
     *  用户发布文章
     */
    @Insert("insert into blobs(b_title, b_author_id, b_author_name, b_author_autograph, b_time, b_text1, b_text2, b_text3, b_picture1, b_picture2, b_picture3)  values(#{b_title},#{b_author_id},#{b_author_name},#{b_author_autograph},#{b_time},#{b_text1},#{b_text2},#{b_text3},#{b_picture1},#{b_picture2},#{b_picture3} )")
    void UserPublishBlober(Blober blober);


    /**
     * 查询当前用户发布的博客文章
     *
     */
    @Select("select * from blobs where b_author_id = #{b_author_id}")
    List<Blober> getUserPublishBlober(@Param("b_author_id") int b_author_id);


    /**
     * 删除指定 b_id 的博客文章信息
     */
    @Delete("delete from blobs where b_id = #{b_id}")
    void deleteBloberByBID(@Param("b_id") int b_id);


    /**
     * 查询推荐的博客信息，按照点击量高低排行
     */
    @Select("select * from blobs order by b_clicked DESC limit 5")
    List<Blober> getTuijianBlogsMsg();


}
