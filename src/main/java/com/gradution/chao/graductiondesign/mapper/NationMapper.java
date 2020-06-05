package com.gradution.chao.graductiondesign.mapper;


import com.gradution.chao.graductiondesign.pojo.NationYao;
import com.gradution.chao.graductiondesign.pojo.NationZhuang;
import com.gradution.chao.graductiondesign.pojo.Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NationMapper {

    @Select("select * from nation_z")
    List<NationZhuang> getNactionZhuang();


    @Select("select * from nation_y")
    List<NationYao> getNactionYao();

    @Select("select * from nation_z where n_id = #{nation_id}")
    NationZhuang getNationZbyID(@Param("nation_id") int natioin_id);

    @Select("select * from nation_y where n_id = #{nation_id}")
    NationYao getNationYbyID(@Param("nation_id") int natioin_id);

    @Update("update nation_z set n_clicked = n_clicked + 1 where n_id = #{nation_id}")
    void addBrowseZhuang(@Param("nation_id") int natioin_id);

    @Update("update nation_y set n_clicked = n_clicked + 1 where n_id = #{nation_id}")
    void addBrowseYao(@Param("nation_id") int natioin_id);

    @Select("select w_id from weather where w_city = #{w_city} limit 1")
    int getWearthIDbyCityName(@Param("w_city") String w_city);

    @Select("select * from point where p_trid = #{p_id}")
    Point getPointbyPtrID(@Param("p_id") int p_id);

    @Select("select * from nation_z order by n_clicked DESC limit 5")
    List<NationZhuang> getTuijianZNationMsg();


    @Select("select * from nation_y order by n_clicked DESC limit 5")
    List<NationYao> getTuijianYNationMsg();


    @Select("select * from nation_z where n_title like #{n_str}")
    List<NationZhuang> getDetailZhuangMsgList(@Param("n_str") String n_str);

    @Select("select * from nation_y where n_title like #{n_str}")
    List<NationYao> getDetailYaoMsgList(@Param("n_str") String n_str);

}
