package com.gradution.chao.graductiondesign.utils;

import java.io.IOException;

import com.gradution.chao.graductiondesign.enumVO.WeatherVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *  负责抓取网站天气信息数据的爬虫Util
 */

public class GetWeathJsoupUtil {

    public WeatherVO weatherVO;



    public Document getDocument(String url) {
        try {
            // 5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取天气信息 今日天气信息
    public WeatherVO getDay1Weather(int cityID, WeatherVO weatherVO) {

        String weatherUrl = "http://www.weather.com.cn/weather/"
                + String.valueOf(cityID) + ".shtml";
        // System.out.println(weatherUrl);
        // Getline1 t = new Getline1();
        Document doc = this.getDocument(weatherUrl);
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=t clearfix]");
        // 今天 代码lv2 与 lv3相互切换
        try {
            Elements elements2 = elements1.select("[class=sky skyid lv2 on]");
            String today = elements2.get(0).text();
            // System.out.println("今日天气： "+today);
        } catch (Exception ex) {
            // ex.printStackTrace();
            try {
                Elements elements2 = elements1
                        .select("[class=sky skyid lv3 on]");
                String today = elements2.get(0).text();
                // System.out.println("今日天气： "+today);
            } catch (Exception ep) {
                try {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv4 on]");
                    String today = elements2.get(0).text();
                    // System.out.println("今日天气： "+today);
                } catch (Exception eb) {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv1 on]");
                    String today = elements2.get(0).text();
                }
            }
        }

        // 几号
        Elements elements3 = elements1.select("h1");
        String number = elements3.get(0).text();
        // System.out.println("日期： "+number);

        // 天气
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(0).text();
        // System.out.println("天气： "+rain);

        // 最高温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(0).text() + "°C";
        // System.out.println("最高温："+highTemperature);

        // 最低温度
        Elements elements6 = elements1.select(".tem i");
        String lowTemperature = elements6.get(0).text();
        // System.out.println("最低温："+lowTemperature);

        // 风力
        Elements elements7 = elements1.select(".win i");
        String wind = elements7.get(2).text();
        // System.out.println("风力："+wind);

        String message1 = number + " 天气:" + rain + " 气温:" + highTemperature
                + "——" + lowTemperature + " 风力:" + wind;

        weatherVO.setDay1Date(number);
        weatherVO.setDay1Tem(lowTemperature);
        weatherVO.setDay1Wea(rain);
        weatherVO.setDay1Wind(wind);
        return weatherVO;
    }


    // 获取天气信息  明日天气信息
    public WeatherVO getDay2Weather(int cityID, WeatherVO weatherVO) {

        String weatherUrl = "http://www.weather.com.cn/weather/"
                + String.valueOf(cityID) + ".shtml";
        // System.out.println(weatherUrl);
        // Getline1 t = new Getline1();
        Document doc = this.getDocument(weatherUrl);
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=t clearfix]");
        // 今天 代码lv2 与 lv3相互切换
        try {
            Elements elements2 = elements1.select("[class=sky skyid lv2]");
            String today = elements2.get(0).text();
            // System.out.println("今日天气： "+today);
        } catch (Exception ex) {
            // ex.printStackTrace();
            try {
                Elements elements2 = elements1
                        .select("[class=sky skyid lv3]");
                String today = elements2.get(0).text();
                // System.out.println("今日天气： "+today);
            } catch (Exception ep) {
                try {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv4]");
                    String today = elements2.get(0).text();
                    // System.out.println("今日天气： "+today);
                } catch (Exception eb) {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv1]");
                    String today = elements2.get(0).text();
                }
            }
        }

        // 几号
        Elements elements3 = elements1.select("h1");
        String number = elements3.get(1).text();
        // System.out.println("日期： "+number);

        // 天气
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(1).text();
        // System.out.println("天气： "+rain);

        // 最高温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(1).text() + "°C";
        // System.out.println("最高温："+highTemperature);

        // 最低温度
        Elements elements6 = elements1.select(".tem i");
        String lowTemperature = elements6.get(1).text();
        // System.out.println("最低温："+lowTemperature);

        // 风力
        Elements elements7 = elements1.select(".win i");
        String wind = elements7.get(1).text();
        // System.out.println("风力："+wind);

        String message1 = number + " 天气:" + rain + " 气温:" + highTemperature
                + "——" + lowTemperature + " 风力:" + wind;

        weatherVO.setDay2Date(number);
        weatherVO.setDay2Tem(lowTemperature);
        weatherVO.setDay2Wea(rain);
        weatherVO.setDay2Wind(wind);
        return weatherVO;
    }

    // 获取天气信息  后天天气信息
    public WeatherVO getDay3Weather(int cityID, WeatherVO weatherVO) {

        String weatherUrl = "http://www.weather.com.cn/weather/"
                + String.valueOf(cityID) + ".shtml";
        // System.out.println(weatherUrl);
        // Getline1 t = new Getline1();
        Document doc = this.getDocument(weatherUrl);
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=t clearfix]");
        // 今天 代码lv2 与 lv3相互切换
        try {
            Elements elements2 = elements1.select("[class=sky skyid lv2]");
            String today = elements2.get(0).text();
            // System.out.println("今日天气： "+today);
        } catch (Exception ex) {
            // ex.printStackTrace();
            try {
                Elements elements2 = elements1
                        .select("[class=sky skyid lv3]");
                String today = elements2.get(0).text();
                // System.out.println("今日天气： "+today);
            } catch (Exception ep) {
                try {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv4]");
                    String today = elements2.get(0).text();
                    // System.out.println("今日天气： "+today);
                } catch (Exception eb) {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv1]");
                    String today = elements2.get(0).text();
                }
            }
        }

        // 几号
        Elements elements3 = elements1.select("h1");
        String number = elements3.get(2).text();
        // System.out.println("日期： "+number);

        // 天气
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(2).text();
        // System.out.println("天气： "+rain);

        // 最高温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(2).text() + "°C";
        // System.out.println("最高温："+highTemperature);

        // 最低温度
        Elements elements6 = elements1.select(".tem i");
        String lowTemperature = elements6.get(2).text();
        // System.out.println("最低温："+lowTemperature);

        // 风力
        Elements elements7 = elements1.select(".win i");
        String wind = elements7.get(2).text();
        // System.out.println("风力："+wind);

        String message1 = number + " 天气:" + rain + " 气温:" + highTemperature
                + "——" + lowTemperature + " 风力:" + wind;

        weatherVO.setDay3Date(number);
        weatherVO.setDay3Tem(lowTemperature);
        weatherVO.setDay3Wea(rain);
        weatherVO.setDay3Wind(wind);
        return weatherVO;
    }


    // 获取天气信息  大后天天气信息
    public WeatherVO getDay4Weather(int cityID, WeatherVO weatherVO) {

        String weatherUrl = "http://www.weather.com.cn/weather/"
                + String.valueOf(cityID) + ".shtml";
        // System.out.println(weatherUrl);
        // Getline1 t = new Getline1();
        Document doc = this.getDocument(weatherUrl);
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=t clearfix]");
        // 今天 代码lv2 与 lv3相互切换
        try {
            Elements elements2 = elements1.select("[class=sky skyid lv2]");
            String today = elements2.get(0).text();
            // System.out.println("今日天气： "+today);
        } catch (Exception ex) {
            // ex.printStackTrace();
            try {
                Elements elements2 = elements1
                        .select("[class=sky skyid lv3]");
                String today = elements2.get(0).text();
                // System.out.println("今日天气： "+today);
            } catch (Exception ep) {
                try {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv4]");
                    String today = elements2.get(0).text();
                    // System.out.println("今日天气： "+today);
                } catch (Exception eb) {
                    Elements elements2 = elements1
                            .select("[class=sky skyid lv1]");
                    String today = elements2.get(0).text();
                }
            }
        }

        // 几号
        Elements elements3 = elements1.select("h1");
        String number = elements3.get(3).text();
        // System.out.println("日期： "+number);

        // 天气
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(3).text();
        // System.out.println("天气： "+rain);

        // 最高温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(3).text() + "°C";
        // System.out.println("最高温："+highTemperature);

        // 最低温度
        Elements elements6 = elements1.select(".tem i");
        String lowTemperature = elements6.get(3).text();
        // System.out.println("最低温："+lowTemperature);

        // 风力
        Elements elements7 = elements1.select(".win i");
        String wind = elements7.get(3).text();
        // System.out.println("风力："+wind);

        String message1 = number + " 天气:" + rain + " 气温:" + highTemperature
                + "——" + lowTemperature + " 风力:" + wind;

        weatherVO.setDay4Date(number);
        weatherVO.setDay4Tem(lowTemperature);
        weatherVO.setDay4Wea(rain);
        weatherVO.setDay4Wind(wind);
        return weatherVO;
    }


    // 获取紫外线指数、穿衣指数(仅限今日)
    public WeatherVO getClothes(int cityID, WeatherVO weatherVO) {

        String weatherUrl = "http://www.weather.com.cn/weather/"
                + String.valueOf(cityID) + ".shtml";

        Document doc = this.getDocument(weatherUrl);
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=clearfix]");
        String today = "";
        try {
            Elements elements2 = elements1.select("[class=li1]");
            Elements elements4 = elements2.select("span");
            today = elements4.get(0).text();
            // System.out.println("紫外线指数: "+today);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String clothes = "";
        try {
            Elements elements3 = elements1.select("[class=li3 hot]");
            Elements elements5 = elements3.select("p");
            clothes = elements5.get(0).text();
            // System.out.println("穿衣指数: "+clothes);
        } catch (Exception ey) {
            ey.printStackTrace();
        }
        String message = "紫外线指数: " + today + "穿衣指数: " + clothes;

        weatherVO.setWea_rays("紫外线指数: " + today);
        weatherVO.setWearcloth("  穿衣指数: " + clothes);
        return weatherVO;
    }


}
