package com.gradution.chao.graductiondesign.service;

import com.gradution.chao.graductiondesign.pojo.Blober;
import com.gradution.chao.graductiondesign.pojo.NationYao;
import com.gradution.chao.graductiondesign.pojo.NationZhuang;
import com.gradution.chao.graductiondesign.pojo.Point;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NationService {

    List<NationZhuang> getNactionZhuang();

    List<NationYao> getNactionYao();


    NationZhuang getNationZbyID(int natioin_id);


    NationYao getNationYbyID(int natioin_id);


    void addBrowseZhuang(int natioin_id);


    void addBrowseYao(int natioin_id);

    int getWearthIDbyCityName(String w_city);

    Point getPointbyPtrID(int p_id);

    List<NationZhuang> getTuijianZNationMsg();

    List<NationYao> getTuijianYNationMsg();

    List<NationZhuang> getDetailZhuangMsgList(String n_str);

    List<NationYao> getDetailYaoMsgList(String n_str);

}
