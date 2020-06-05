package com.gradution.chao.graductiondesign.enumVO;

/**
 * 该类用户传输到前端的天气类
 * 通过Jsoup爬取天气数据
 * 爬取内容为，今日，明天，后天，大后天的天气数据
 */

public class WeatherVO {

    //第1天天气数据
    private String day1Date;
    private String day1Tem;
    private String day1Wea;
    private String day1Wind;
    //第2天天气数据
    private String day2Date;
    private String day2Tem;
    private String day2Wea;
    private String day2Wind;
    //第3天天气数据
    private String day3Date;
    private String day3Tem;
    private String day3Wea;
    private String day3Wind;
    //第4天天气数据
    private String day4Date;
    private String day4Tem;
    private String day4Wea;
    private String day4Wind;
    //紫外线指数，穿衣指数
    private String Wea_rays;
    private String Wearcloth;

    public String getDay1Tem() {
        return day1Tem;
    }

    public void setDay1Tem(String day1Tem) {
        this.day1Tem = day1Tem;
    }

    public String getDay1Wea() {
        return day1Wea;
    }

    public void setDay1Wea(String day1Wea) {
        this.day1Wea = day1Wea;
    }

    public String getDay1Wind() {
        return day1Wind;
    }

    public void setDay1Wind(String day1Wind) {
        this.day1Wind = day1Wind;
    }

    public String getDay2Tem() {
        return day2Tem;
    }

    public void setDay2Tem(String day2Tem) {
        this.day2Tem = day2Tem;
    }

    public String getDay2Wea() {
        return day2Wea;
    }

    public void setDay2Wea(String day2Wea) {
        this.day2Wea = day2Wea;
    }

    public String getDay2Wind() {
        return day2Wind;
    }

    public void setDay2Wind(String day2Wind) {
        this.day2Wind = day2Wind;
    }

    public String getDay3Tem() {
        return day3Tem;
    }

    public void setDay3Tem(String day3Tem) {
        this.day3Tem = day3Tem;
    }

    public String getDay3Wea() {
        return day3Wea;
    }

    public void setDay3Wea(String day3Wea) {
        this.day3Wea = day3Wea;
    }

    public String getDay3Wind() {
        return day3Wind;
    }

    public void setDay3Wind(String day3Wind) {
        this.day3Wind = day3Wind;
    }

    public String getDay4Tem() {
        return day4Tem;
    }

    public void setDay4Tem(String day4Tem) {
        this.day4Tem = day4Tem;
    }

    public String getDay4Wea() {
        return day4Wea;
    }

    public void setDay4Wea(String day4Wea) {
        this.day4Wea = day4Wea;
    }

    public String getDay4Wind() {
        return day4Wind;
    }

    public void setDay4Wind(String day4Wind) {
        this.day4Wind = day4Wind;
    }

    public String getWea_rays() {
        return Wea_rays;
    }

    public void setWea_rays(String wea_rays) {
        Wea_rays = wea_rays;
    }

    public String getWearcloth() {
        return Wearcloth;
    }

    public void setWearcloth(String wearcloth) {
        Wearcloth = wearcloth;
    }

    public String getDay1Date() {
        return day1Date;
    }

    public void setDay1Date(String day1Date) {
        this.day1Date = day1Date;
    }

    public String getDay2Date() {
        return day2Date;
    }

    public void setDay2Date(String day2Date) {
        this.day2Date = day2Date;
    }

    public String getDay3Date() {
        return day3Date;
    }

    public void setDay3Date(String day3Date) {
        this.day3Date = day3Date;
    }

    public String getDay4Date() {
        return day4Date;
    }

    public void setDay4Date(String day4Date) {
        this.day4Date = day4Date;
    }

    @Override
    public String toString() {
        return "WeatherVO{" +
                "day1Date='" + day1Date + '\'' +
                ", day1Tem='" + day1Tem + '\'' +
                ", day1Wea='" + day1Wea + '\'' +
                ", day1Wind='" + day1Wind + '\'' +
                ", day2Date='" + day2Date + '\'' +
                ", day2Tem='" + day2Tem + '\'' +
                ", day2Wea='" + day2Wea + '\'' +
                ", day2Wind='" + day2Wind + '\'' +
                ", day3Date='" + day3Date + '\'' +
                ", day3Tem='" + day3Tem + '\'' +
                ", day3Wea='" + day3Wea + '\'' +
                ", day3Wind='" + day3Wind + '\'' +
                ", day4Date='" + day4Date + '\'' +
                ", day4Tem='" + day4Tem + '\'' +
                ", day4Wea='" + day4Wea + '\'' +
                ", day4Wind='" + day4Wind + '\'' +
                ", Wea_rays='" + Wea_rays + '\'' +
                ", Wearcloth='" + Wearcloth + '\'' +
                '}';
    }
}
