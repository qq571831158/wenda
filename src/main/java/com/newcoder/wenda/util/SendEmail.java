package com.newcoder.wenda.util;

import org.apache.commons.mail.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by apple on 2017/7/17.
 */
public class SendEmail{
    private  String fromUser;
    private  String fromPass;
    private String toemail;
    private String code;
    public String getToemail() {
        return toemail;
    }
    public SendEmail() {
        Properties pps = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("/common.properties");
            pps.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fromUser = pps.getProperty("mail.fromUser");
        this.fromPass = pps.getProperty("mail.fromPass");

    }

    public void setToemail(String toemail) {
        this.toemail = toemail;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    //发送普通邮件
    public  void sendText(String toemail,String contents)
    {
        SimpleEmail email = new SimpleEmail();
        email.setTLS(true);
        email.setHostName("smtp.163.com");
        email.setAuthentication(fromUser, fromPass);   //用户名和密码
        try
        {
            email.addTo(toemail); //接收方
            email.setContent(contents , "text/html;charset=utf-8");
            email.setFrom(fromUser);       //发送方
            email.setSubject("登录异常");         //标题
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
    //发送普通邮件
    public  void sendHtml()
    {
        HtmlEmail email = new HtmlEmail();
        email.setTLS(true);
        email.setHostName("smtp.163.com");
        email.setAuthentication(fromUser, fromPass);   //用户名和密码
        try
        {
            email.addTo("571831158@qq.com"); //接收方
            email.setFrom(fromUser);       //发送方
            email.setSubject(fromPass);         //标题
            //email.setContent(content , "text/html;charset=utf-8");
            //email.setCharset( "text/html;charset=utf-8");
            email.setHtmlMsg("Just a simple s第三代 .<input name=\"userID\" type=\"text\" id=\"userID\">\n" +
                    "         <input name=\"password\" type=\"password\" id=\"password\">\n" +
                    "         <input name=\"s3\" type=\"button\" id=\"btn\" value=\"按钮\">");   //内容
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
    //发送带附件的邮件
    public  void sendAttachment(){
        //创建一个Email附件
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setPath("F:/资料/实验室人事项目/图片/logo.png");
        //emailAttachment.setURL(new URL("http://www.blogjava.net/bulktree/picture/bulktree.jpg"));
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        emailAttachment.setDescription("This is Smile picture");
        //emailAttachment.setName("bulktree");//若不设置该方法将使用原文件名称
        // 创建一个email
        MultiPartEmail multipartemail = new MultiPartEmail();
        multipartemail.setTLS(true);
        multipartemail.setHostName("smtp.gmail.com");
        multipartemail.setAuthentication("***@gmail.com", "***");
        try {
            multipartemail.addTo("***@163.com", "username");
            multipartemail.setFrom("***@gmail.com", "工作室");
            multipartemail.setSubject("This is a attachment Email");
            multipartemail.setMsg("this a attachment Eamil Test");
            multipartemail.attach(emailAttachment); //添加附件
            multipartemail.send(); //发送邮件
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

}
