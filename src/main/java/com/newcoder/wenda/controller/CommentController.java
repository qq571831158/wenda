package com.newcoder.wenda.controller;

import com.newcoder.wenda.model.Comment;
import com.newcoder.wenda.model.EntityType;
import com.newcoder.wenda.model.HostHolder;
import com.newcoder.wenda.service.CommentService;
import com.newcoder.wenda.util.WendaUtil;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by apple on 2017/8/29.
 */
@Controller
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    HostHolder hostHolder;
    @RequestMapping(path = {"addComment"},method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId")int questionId,
                             @RequestParam("content")String content){
        try{
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null){
                comment.setUserId(hostHolder.getUser().getId());
            }else {
                comment.setUserId(WendaUtil.ANOUSER_id);
//            return "redirct:/reglogin";
            }
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            commentService.addComment(comment);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("增加评论失败",e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }
}
