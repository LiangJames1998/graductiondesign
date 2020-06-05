package com.gradution.chao.graductiondesign.service.impl;

import com.gradution.chao.graductiondesign.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements SendMailService{

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendQQMail(String title, String sendTo, String contenxt) {

        try{
            MimeMessage mineMessageage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mineMessageage);
            //发送方
            message.setFrom(from);
            //主题
            message.setSubject(title);
            //邮件接受方
            message.setTo(sendTo);
            //内容
            message.setText(contenxt);

            this.mailSender.send(mineMessageage);

        }catch (Exception ex){
            System.out.println(ex.toString());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


    @Override
    public void sendHtmlQQMail(String sendTo, String subject, String content) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(sendTo);
            //邮件主题
            message.setSubject(subject);
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
