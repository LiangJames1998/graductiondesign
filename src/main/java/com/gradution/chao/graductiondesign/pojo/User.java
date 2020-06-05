package com.gradution.chao.graductiondesign.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户实体类
 */
public class User {

    private int u_id;
    private String u_account;
    private String u_password;
    private String u_realname;
    private String u_autograph;
    private String u_headimg;
    private String u_tel;
    private String u_email;
    private String u_hometown;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date   u_birthday;
    private String u_job;


    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_account() {
        return u_account;
    }

    public void setU_account(String u_account) {
        this.u_account = u_account;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_realname() {
        return u_realname;
    }

    public void setU_realname(String u_realname) {
        this.u_realname = u_realname;
    }

    public String getU_autograph() {
        return u_autograph;
    }

    public void setU_autograph(String u_autograph) {
        this.u_autograph = u_autograph;
    }

    public String getU_headimg() {
        return u_headimg;
    }

    public void setU_headimg(String u_headimg) {
        this.u_headimg = u_headimg;
    }

    public String getU_tel() {
        return u_tel;
    }

    public void setU_tel(String u_tel) {
        this.u_tel = u_tel;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_hometown() {
        return u_hometown;
    }

    public void setU_hometown(String u_hometown) {
        this.u_hometown = u_hometown;
    }

    public Date getU_birthday() {
        return u_birthday;
    }

    public void setU_birthday(Date u_birthday) {
        this.u_birthday = u_birthday;
    }

    public String getU_job() {
        return u_job;
    }

    public void setU_job(String u_job) {
        this.u_job = u_job;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", u_account='" + u_account + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_realname='" + u_realname + '\'' +
                ", u_autograph='" + u_autograph + '\'' +
                ", u_headimg='" + u_headimg + '\'' +
                ", u_tel='" + u_tel + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_hometown='" + u_hometown + '\'' +
                ", u_birthday=" + u_birthday +
                ", u_job='" + u_job + '\'' +
                '}';
    }
}
