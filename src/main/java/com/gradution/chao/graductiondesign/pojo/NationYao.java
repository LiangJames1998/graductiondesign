package com.gradution.chao.graductiondesign.pojo;

import java.util.Date;

public class NationYao {

    private int n_id;
    private String n_title;
    private Date n_time;
    private int n_clicked;
    private String n_content1;
    private String n_content2;
    private String n_content3;
    private String n_picture1;
    private String n_picture2;
    private String n_picture3;
    private String n_picture4;


    public int getN_id() {
        return n_id;
    }

    public void setN_id(int n_id) {
        this.n_id = n_id;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public Date getN_time() {
        return n_time;
    }

    public void setN_time(Date n_time) {
        this.n_time = n_time;
    }

    public int getN_clicked() {
        return n_clicked;
    }

    public void setN_clicked(int n_clicked) {
        this.n_clicked = n_clicked;
    }

    public String getN_content1() {
        return n_content1;
    }

    public void setN_content1(String n_content1) {
        this.n_content1 = n_content1;
    }

    public String getN_content2() {
        return n_content2;
    }

    public void setN_content2(String n_content2) {
        this.n_content2 = n_content2;
    }

    public String getN_content3() {
        return n_content3;
    }

    public void setN_content3(String n_content3) {
        this.n_content3 = n_content3;
    }

    public String getN_picture1() {
        return n_picture1;
    }

    public void setN_picture1(String n_picture1) {
        this.n_picture1 = n_picture1;
    }

    public String getN_picture2() {
        return n_picture2;
    }

    public void setN_picture2(String n_picture2) {
        this.n_picture2 = n_picture2;
    }

    public String getN_picture3() {
        return n_picture3;
    }

    public void setN_picture3(String n_picture3) {
        this.n_picture3 = n_picture3;
    }

    public String getN_picture4() {
        return n_picture4;
    }

    public void setN_picture4(String n_picture4) {
        this.n_picture4 = n_picture4;
    }

    @Override
    public String toString() {
        return "NationYao{" +
                "n_id=" + n_id +
                ", n_title='" + n_title + '\'' +
                ", n_time=" + n_time +
                ", n_clicked=" + n_clicked +
                ", n_content1='" + n_content1 + '\'' +
                ", n_content2='" + n_content2 + '\'' +
                ", n_content3='" + n_content3 + '\'' +
                ", n_picture1='" + n_picture1 + '\'' +
                ", n_picture2='" + n_picture2 + '\'' +
                ", n_picture3='" + n_picture3 + '\'' +
                ", n_picture4='" + n_picture4 + '\'' +
                '}';
    }
}
