package com.gradution.chao.graductiondesign.pojo;


import java.util.Date;

public class Browseblog {

    private int b_id;
    private int b_userid;
    private int b_blogid;
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

    public int getB_blogid() {
        return b_blogid;
    }

    public void setB_blogid(int b_blogid) {
        this.b_blogid = b_blogid;
    }

    public Date getB_time() {
        return b_time;
    }

    public void setB_time(Date b_time) {
        this.b_time = b_time;
    }


    @Override
    public String toString() {
        return "Browseblog{" +
                "b_id=" + b_id +
                ", b_userid=" + b_userid +
                ", b_blogid=" + b_blogid +
                ", b_time=" + b_time +
                '}';
    }
}
