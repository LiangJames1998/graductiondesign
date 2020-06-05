package com.gradution.chao.graductiondesign.service.impl;

import com.gradution.chao.graductiondesign.mapper.NationMapper;
import com.gradution.chao.graductiondesign.pojo.Blober;
import com.gradution.chao.graductiondesign.pojo.NationYao;
import com.gradution.chao.graductiondesign.pojo.NationZhuang;
import com.gradution.chao.graductiondesign.pojo.Point;
import com.gradution.chao.graductiondesign.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationServiceImpl implements NationService {

    @Autowired
    private NationMapper nationMapper;

    @Override
    public List<NationZhuang> getNactionZhuang() {
        return nationMapper.getNactionZhuang();
    }

    @Override
    public List<NationYao> getNactionYao() {
        return nationMapper.getNactionYao();
    }

    @Override
    public NationZhuang getNationZbyID(int natioin_id) {
        return nationMapper.getNationZbyID(natioin_id);
    }

    @Override
    public NationYao getNationYbyID(int natioin_id) {
        return nationMapper.getNationYbyID(natioin_id);
    }

    @Override
    public void addBrowseZhuang(int natioin_id) {
        nationMapper.addBrowseZhuang(natioin_id);
    }

    @Override
    public void addBrowseYao(int natioin_id) {
        nationMapper.addBrowseYao(natioin_id);
    }


    @Override
    public int getWearthIDbyCityName(String w_city) {
        return nationMapper.getWearthIDbyCityName(w_city);
    }


    @Override
    public Point getPointbyPtrID(int p_id) {
        return nationMapper.getPointbyPtrID(p_id);
    }


    @Override
    public List<NationZhuang> getTuijianZNationMsg() {
        return nationMapper.getTuijianZNationMsg();
    }

    @Override
    public List<NationYao> getTuijianYNationMsg() {
        return nationMapper.getTuijianYNationMsg();
    }

    @Override
    public List<NationZhuang> getDetailZhuangMsgList(String n_str) {
        return nationMapper.getDetailZhuangMsgList(n_str);
    }

    @Override
    public List<NationYao> getDetailYaoMsgList(String n_str) {
        return nationMapper.getDetailYaoMsgList(n_str);
    }
}
