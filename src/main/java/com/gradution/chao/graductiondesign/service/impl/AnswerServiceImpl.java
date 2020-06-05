package com.gradution.chao.graductiondesign.service.impl;

import com.gradution.chao.graductiondesign.service.AnswerService;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AnswerServiceImpl implements AnswerService {


    @Override
    public String answerQuestinoOne(String cotent) {

        String answer = "";
        String result = "";
        try {
            //创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建httpGet实例
            String goingUrl = "https://baike.baidu.com/item/"+cotent;
            HttpGet httpGet = new HttpGet(goingUrl);
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response != null){
                HttpEntity entity =  response.getEntity();  //获取网页内容
                result = EntityUtils.toString(entity, "gbk");
                //System.out.println(result);
                Document doc = Jsoup.parse(result);
                //截取答案信息
                Elements elements1=doc.select("[class=poster-top]");
                //具体挑选
                Elements elements2=elements1.select("[class=para]");
                answer=elements2.get(0).text();
                //System.out.println(answer);

            }
            if (response != null){
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }

        }catch (Exception e){
            //e.printStackTrace();
            try {
                Document doc2 = Jsoup.parse(result);
                //截取答案信息
                Elements elements3=doc2.select("[class=main-content]");
                System.out.println(elements3.get(0).text());
                //具体挑选
                Elements elements4=elements3.select("[class=lemma-summary]");
                answer=elements4.get(0).text();
            }catch (Exception e1){

                return "";
            }
        }
        return answer;
    }

    @Override
    public String answerQuestinoTwo(String cotent) {

        String answer = "";

        try {
            //创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建httpGet实例
            String goingUrl = "http://www.baidu.com/s?wd="+cotent+"&cl=3";
            HttpGet httpGet = new HttpGet(goingUrl);
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response != null){
                HttpEntity entity =  response.getEntity();  //获取网页内容
                String result = EntityUtils.toString(entity, "UTF-8");
                //System.out.println("网页内容:"+result);

                Document doc = Jsoup.parse(result);
                //截取答案信息
                Elements elements1=doc.select("[class=op_exactqa_s_area c-span18 c-span-last]");
                //具体挑选
                Elements elements2=elements1.select("[class=op_exactqa_s_answer]");
                answer=elements2.get(0).text();
                //System.out.println(answer);
            }
            if (response != null){
                response.close();
            }
            if (httpClient != null){
                httpClient.close();
            }

        }catch (Exception e){
            //e.printStackTrace();
            return "";
        }

        return answer;
    }

    @Override
    public String answerQuestinoThree(String cotent) {
        String answer = "";
        try {

            //创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建httpGet实例
            String goingUrl = "https://www.sogou.com/sogou?query="+cotent+"&_ast=1590329287&_asf=www.sogou.com&w=01029901&pid=sogou-wsse-a9e18cb5dd9d3ab4&duppid=1&cid=&s_from=result_up&insite=wenwen.sogou.com";
            HttpGet httpGet = new HttpGet(goingUrl);
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response != null){
                HttpEntity entity =  response.getEntity();  //获取网页内容
                String result = EntityUtils.toString(entity, "gbk");

                Document doc = Jsoup.parse(result);
                //截取答案信息
                Elements elements1=doc.select("[class=vrwrap]");
                //具体挑选
                Elements elements2=elements1.select("[class=str-text-info]");
                answer=elements2.get(0).text();
                //System.out.println(answer);
            }
            if (response != null){
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }

        }catch (Exception e){
            //e.printStackTrace();
            return "";
        }

        return answer;
    }
}
