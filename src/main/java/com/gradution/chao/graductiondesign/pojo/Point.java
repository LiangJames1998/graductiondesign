package com.gradution.chao.graductiondesign.pojo;

/**
 * 坐标类
 */

public class Point {

    private int p_trid;
    private String p_x;
    private String p_y;

    public int getP_trid() {
        return p_trid;
    }

    public void setP_trid(int p_trid) {
        this.p_trid = p_trid;
    }

    public String getP_x() {
        return p_x;
    }

    public void setP_x(String p_x) {
        this.p_x = p_x;
    }

    public String getP_y() {
        return p_y;
    }

    public void setP_y(String p_y) {
        this.p_y = p_y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "p_trid=" + p_trid +
                ", p_x='" + p_x + '\'' +
                ", p_y='" + p_y + '\'' +
                '}';
    }
}
