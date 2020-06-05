package com.gradution.chao.graductiondesign.pojo;

import java.util.Date;

public class Comment {


    private int c_id;
    private int c_articleid;
    private String c_person;
    private String c_person_img;
    private Date c_time;
    private String c_text;

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getC_articleid() {
        return c_articleid;
    }

    public void setC_articleid(int c_articleid) {
        this.c_articleid = c_articleid;
    }

    public String getC_person() {
        return c_person;
    }

    public void setC_person(String c_person) {
        this.c_person = c_person;
    }

    public String getC_person_img() {
        return c_person_img;
    }

    public void setC_person_img(String c_person_img) {
        this.c_person_img = c_person_img;
    }

    public Date getC_time() {
        return c_time;
    }

    public void setC_time(Date c_time) {
        this.c_time = c_time;
    }

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "c_id=" + c_id +
                ", c_articleid=" + c_articleid +
                ", c_person='" + c_person + '\'' +
                ", c_person_img='" + c_person_img + '\'' +
                ", c_time=" + c_time +
                ", c_text='" + c_text + '\'' +
                '}';
    }
}
