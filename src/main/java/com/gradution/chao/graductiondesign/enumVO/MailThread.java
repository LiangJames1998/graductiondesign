package com.gradution.chao.graductiondesign.enumVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class MailThread implements Runnable{

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    private String sendTo;
    private String title;
    private String content;

    public MailThread(String sendTo,String title,String content){
        this.sendTo = sendTo;
        this.title = title;
        this.content = content;
    }


    @Override
    public void run() {
        System.out.println("开始发送邮件");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(sendTo);
            //邮件主题
            message.setSubject(title);
            //邮件内容，html格式
            messageHelper.setText(content, true);

            //发送
            mailSender.send(message);
            //日志信息
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
