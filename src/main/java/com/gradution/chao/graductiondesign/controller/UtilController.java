package com.gradution.chao.graductiondesign.controller;


import com.gradution.chao.graductiondesign.utils.CpachaUtil;
import com.gradution.chao.graductiondesign.utils.Result;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@Controller
@RequestMapping("/travel")
public class UtilController {


    /**
     * 显示验证码
     * @param request
     * @param vl
     * @param w
     * @param h
     * @param response
     */

    @RequestMapping(value="/get_cpacha",method= RequestMethod.GET)
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
                          @RequestParam(value="w",defaultValue="98",required=false) Integer w,
                          @RequestParam(value="h",defaultValue="33",required=false) Integer h,
                          HttpServletResponse response){

        //System.out.println("创建新的验证码！");

        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha", generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取天气api，还需转成实体类
     * @return
     */
    @RequestMapping(value="/getWeatherForAPI",method= RequestMethod.POST)
    @ResponseBody
    public String getWeatherMsgByID(@RequestParam("weathID") Integer weathID){
        try {
            String waethUrl = "http://wthrcdn.etouch.cn/weather_mini?citykey="+ weathID;
            URL realUrl = new URL(waethUrl);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setRequestMethod("GET");// 提交模式
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "close");
            conn.setConnectTimeout(3000);  //设置连接主机超时（单位：毫秒）
            conn.setReadTimeout(2000);     //设置从主机读取数据超时（单位：毫秒）
            InputStream stream = new GZIPInputStream(conn.getInputStream());
            String str = IOUtils.toString(stream,"utf-8");

            return str;

        }catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }



}
