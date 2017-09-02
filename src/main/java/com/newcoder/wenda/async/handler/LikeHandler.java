package com.newcoder.wenda.async.handler;

import com.newcoder.wenda.async.EventHandler;
import com.newcoder.wenda.async.EventModel;
import com.newcoder.wenda.async.EventType;
import com.newcoder.wenda.model.Message;
import com.newcoder.wenda.model.User;
import com.newcoder.wenda.service.MessageService;
import com.newcoder.wenda.service.UserService;
import com.newcoder.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 2017/9/1.
 */
@Component
public class LikeHandler implements EventHandler {
    private static int SYSTEM_USERID=4;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        message.setFromId(SYSTEM_USERID);
        message.setToId(model.getEntitiOnwerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户"+user.getName()+"赞了你的评论，http://127.0.0.1:8080/question/"+model.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
