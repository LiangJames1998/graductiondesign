package com.gradution.chao.graductiondesign.pojo;

import java.util.Date;

/**
 * 多项合并类，用于打包送至前端进行展示
 *
 */

public class BrowseCombine {


    private int br_id;
    private int br_userid;
    private int br_blogid;
    private Date br_time;

    private String br_title;
    private String br_author;
    private int br_clicked;
    private int br_comment;

    private String br_city;
    //民俗特点
    private String br_customer;


    public String getBr_customer() {
        return br_customer;
    }

    public void setBr_customer(String br_customer) {
        this.br_customer = br_customer;
    }

    public String getBr_city() {
        return br_city;
    }

    public void setBr_city(String br_city) {
        this.br_city = br_city;
    }

    public int getBr_id() {
        return br_id;
    }

    public void setBr_id(int br_id) {
        this.br_id = br_id;
    }

    public int getBr_userid() {
        return br_userid;
    }

    public void setBr_userid(int br_userid) {
        this.br_userid = br_userid;
    }

    public int getBr_blogid() {
        return br_blogid;
    }

    public void setBr_blogid(int br_blogid) {
        this.br_blogid = br_blogid;
    }

    public Date getBr_time() {
        return br_time;
    }

    public void setBr_time(Date br_time) {
        this.br_time = br_time;
    }

    public String getBr_title() {
        return br_title;
    }

    public void setBr_title(String br_title) {
        this.br_title = br_title;
    }

    public String getBr_author() {
        return br_author;
    }

    public void setBr_author(String br_author) {
        this.br_author = br_author;
    }

    public int getBr_clicked() {
        return br_clicked;
    }

    public void setBr_clicked(int br_clicked) {
        this.br_clicked = br_clicked;
    }

    public int getBr_comment() {
        return br_comment;
    }

    public void setBr_comment(int br_comment) {
        this.br_comment = br_comment;
    }


}
