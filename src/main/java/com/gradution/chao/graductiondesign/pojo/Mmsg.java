package com.gradution.chao.graductiondesign.pojo;



public class Mmsg {

    private int m_id;
    //存储的类型
    private int m_type;
    //存储的信息
    private String m_msg;
    //存储的实体类的id
    private int m_mid;

    private String m_title;

    private int m_clicked;

    private String m_mark;

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getM_type() {
        return m_type;
    }

    public void setM_type(int m_type) {
        this.m_type = m_type;
    }

    public String getM_msg() {
        return m_msg;
    }

    public void setM_msg(String m_msg) {
        this.m_msg = m_msg;
    }

    public int getM_mid() {
        return m_mid;
    }

    public void setM_mid(int m_mid) {
        this.m_mid = m_mid;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public int getM_clicked() {
        return m_clicked;
    }

    public void setM_clicked(int m_clicked) {
        this.m_clicked = m_clicked;
    }

    public String getM_mark() {
        return m_mark;
    }

    public void setM_mark(String m_mark) {
        this.m_mark = m_mark;
    }

    @Override
    public String toString() {
        return "Mmsg{" +
                "m_id=" + m_id +
                ", m_type=" + m_type +
                ", m_msg='" + m_msg + '\'' +
                ", m_mid=" + m_mid +
                ", m_title='" + m_title + '\'' +
                ", m_clicked=" + m_clicked +
                ", m_mark='" + m_mark + '\'' +
                '}';
    }
}
