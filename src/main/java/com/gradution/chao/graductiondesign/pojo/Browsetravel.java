package com.gradution.chao.graductiondesign.pojo;

import java.util.Date;

public class Browsetravel {

    private int b_id;
    private int b_userid;
    private int b_travelid;
    private Date b_time;


    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getB_userid() {
        return b_userid;
    }

    public void setB_userid(int b_userid) {
        this.b_userid = b_userid;
    }

    public int getB_travelid() {
        return b_travelid;
    }

    public void setB_travelid(int b_travelid) {
        this.b_travelid = b_travelid;
    }

    public Date getB_time() {
        return b_time;
    }

    public void setB_time(Date b_time) {
        this.b_time = b_time;
    }

    @Override
    public String toString() {
        return "Browsetravel{" +
                "b_id=" + b_id +
                ", b_userid=" + b_userid +
                ", b_travelid=" + b_travelid +
                ", b_time=" + b_time +
                '}';
    }
}
