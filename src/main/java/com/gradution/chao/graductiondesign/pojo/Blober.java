package com.gradution.chao.graductiondesign.pojo;

import java.util.Date;

public class Blober {

    private int b_id;
    private String b_title;
    private int b_author_id;
    private String b_author_name;
    private String b_author_autograph;
    private Date b_time;
    private int b_clicked;
    private int b_comment;
    private String b_text1;
    private String b_text2;
    private String b_text3;
    private String b_picture1;
    private String b_picture2;
    private String b_picture3;


    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public String getB_title() {
        return b_title;
    }

    public void setB_title(String b_title) {
        this.b_title = b_title;
    }

    public int getB_author_id() {
        return b_author_id;
    }

    public void setB_author_id(int b_author_id) {
        this.b_author_id = b_author_id;
    }

    public String getB_author_name() {
        return b_author_name;
    }

    public void setB_author_name(String b_author_name) {
        this.b_author_name = b_author_name;
    }

    public String getB_author_autograph() {
        return b_author_autograph;
    }

    public void setB_author_autograph(String b_author_autograph) {
        this.b_author_autograph = b_author_autograph;
    }

    public Date getB_time() {
        return b_time;
    }

    public void setB_time(Date b_time) {
        this.b_time = b_time;
    }

    public int getB_clicked() {
        return b_clicked;
    }

    public void setB_clicked(int b_clicked) {
        this.b_clicked = b_clicked;
    }

    public int getB_comment() {
        return b_comment;
    }

    public void setB_comment(int b_comment) {
        this.b_comment = b_comment;
    }

    public String getB_picture1() {
        return b_picture1;
    }

    public void setB_picture1(String b_picture1) {
        this.b_picture1 = b_picture1;
    }

    public String getB_picture2() {
        return b_picture2;
    }

    public void setB_picture2(String b_picture2) {
        this.b_picture2 = b_picture2;
    }

    public String getB_picture3() {
        return b_picture3;
    }

    public void setB_picture3(String b_picture3) {
        this.b_picture3 = b_picture3;
    }

    public String getB_text1() {
        return b_text1;
    }

    public void setB_text1(String b_text1) {
        this.b_text1 = b_text1;
    }

    public String getB_text2() {
        return b_text2;
    }

    public void setB_text2(String b_text2) {
        this.b_text2 = b_text2;
    }

    public String getB_text3() {
        return b_text3;
    }

    public void setB_text3(String b_text3) {
        this.b_text3 = b_text3;
    }


    @Override
    public String toString() {
        return "Blober{" +
                "b_id=" + b_id +
                ", b_title='" + b_title + '\'' +
                ", b_author_id=" + b_author_id +
                ", b_author_name='" + b_author_name + '\'' +
                ", b_author_autograph='" + b_author_autograph + '\'' +
                ", b_time=" + b_time +
                ", b_clicked=" + b_clicked +
                ", b_comment=" + b_comment +
                ", b_text1='" + b_text1 + '\'' +
                ", b_text2='" + b_text2 + '\'' +
                ", b_text3='" + b_text3 + '\'' +
                ", b_picture1='" + b_picture1 + '\'' +
                ", b_picture2='" + b_picture2 + '\'' +
                ", b_picture3='" + b_picture3 + '\'' +
                '}';
    }
}
