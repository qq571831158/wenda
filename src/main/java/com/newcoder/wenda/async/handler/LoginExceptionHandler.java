package com.newcoder.wenda.async.handler;

import com.newcoder.wenda.async.EventHandler;
import com.newcoder.wenda.async.EventModel;
import com.newcoder.wenda.async.EventType;
import com.newcoder.wenda.util.MailSender;
import com.newcoder.wenda.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2017/9/2.
 */
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandler(EventModel model) {
        SendEmail sendEmail = new SendEmail();
        //判断发现这个用户登录异常
        Map<String,Object>map = new HashMap<>();
        map.put("username",model.getExt("username"));
        sendEmail.sendText(model.getExt("email"),map.get("username").toString()+"你的登录有异常");
//        mailSender.sendWithHTMLTemplate(model.getExt("email"),"登录IP异常",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
