package com.gradution.chao.graductiondesign.pojo;

public class Admins {

    private int m_id;
    private String m_account;
    private String m_password;
    private String m_headimg;
    private String m_realname;

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_account() {
        return m_account;
    }

    public void setM_account(String m_account) {
        this.m_account = m_account;
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password;
    }

    public String getM_headimg() {
        return m_headimg;
    }

    public void setM_headimg(String m_headimg) {
        this.m_headimg = m_headimg;
    }

    public String getM_realname() {
        return m_realname;
    }

    public void setM_realname(String m_realname) {
        this.m_realname = m_realname;
    }

    @Override
    public String toString() {
        return "Admins{" +
                "m_id=" + m_id +
                ", m_account='" + m_account + '\'' +
                ", m_password='" + m_password + '\'' +
                ", m_headimg='" + m_headimg + '\'' +
                ", m_realname='" + m_realname + '\'' +
                '}';
    }
}
