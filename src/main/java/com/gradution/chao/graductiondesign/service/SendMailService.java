package com.gradution.chao.graductiondesign.service;

public interface SendMailService {

    /**
     * 发送 邮件 Service
     * @param title
     * @param sendTo
     * @param contenxt
     * @return
     */
    boolean sendQQMail(String title, String sendTo, String contenxt);


    /**
     *  发送 HTML 邮件文章
     */
    void sendHtmlQQMail(String sendTo,String subject, String content);


}
