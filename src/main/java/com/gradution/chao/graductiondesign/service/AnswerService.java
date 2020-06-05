package com.gradution.chao.graductiondesign.service;

public interface AnswerService {

    /**
     * 分级别回答用户问题：
     * （1）百科百科词条
     * （2）百度网页直接答案
     * （3）搜狗问问页面数据
     * @param cotent
     * @return
     */

    String answerQuestinoOne(String cotent);

    String answerQuestinoTwo(String cotent);

    String answerQuestinoThree(String cotent);



}
