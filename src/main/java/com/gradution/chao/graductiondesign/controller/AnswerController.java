package com.gradution.chao.graductiondesign.controller;


import com.gradution.chao.graductiondesign.enumVO.MailThread;
import com.gradution.chao.graductiondesign.service.*;
import com.gradution.chao.graductiondesign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/travel")
public class AnswerController {


    @Autowired
    AnswerService answerService;

    @Autowired
    SendMailService sendMailService;

    @Autowired
    TemplateEngine templateEngine;

    private static String url = "http://127.0.0.1:9000";
    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";


    /**
     * 跳转智能问答界面
     * @return
     */
    @RequestMapping("/answeruser")
    public String answerUserView(){
        return "answeruser";
    }


    @RequestMapping(value = "/answerUserMsg",method = RequestMethod.POST)
    @ResponseBody
    public Result AnswerUserThread(@RequestParam("contentFor") String content){

        //System.out.println(content);
        //String str = "驾车路线：全程约2519.8公里 起点：南宁市 1.南宁市内驾车方案 从起点向东南方向出发，沿嘉宾路行驶90米，左转进入金洲路.....";
        String str = "";
        /*  以此按照优先级由高到低进行提问  */
        try {
            String answ1 = answerService.answerQuestinoOne(content);
            String answ2 = answerService.answerQuestinoTwo(content);
            String answ3 = answerService.answerQuestinoThree(content);
            if (!answ1.equals("")){
                str = answ1;
                //System.out.println("1"+str);
            }else if (!answ2.equals("")){
                str = answ2;
                //System.out.println("2"+str);
            }else if (!answ3.equals("")){
                str = answ3;
                //System.out.println("3"+answ3);
            }else if (answ3.equals("")){
                str = "这个问题查不到信息，请换个问题哦!";
            }

        }catch (Exception e){
            e.printStackTrace();
            //str = "这个问题查不到信息，请换个问题哦!";
        }


        Result<String> result = new Result(200,"success",str);

        return (result);
    }

}
