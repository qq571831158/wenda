package com.newcoder.wenda.controller;

import com.newcoder.wenda.model.*;
import com.newcoder.wenda.service.CommentService;
import com.newcoder.wenda.service.QuestionService;
import com.newcoder.wenda.service.UserService;
import com.newcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 2017/8/28.
 */
@Controller
public class QuestionContoller {
    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(QuestionContoller.class);

    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title")String title,@RequestParam("content")String content){
        try{
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreateDate(new Date());
            question.setCommentCount(0);
            if (hostHolder.getUser() == null){
                //question.setUserId(WendaUtil.ANOUSER_id);
                return WendaUtil.getJSONString(999);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question)>0){
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("增加题目失败",e.getMessage());
        }
        return WendaUtil.getJSONString(1,"失败");
    }

    @RequestMapping(value = "question/{qid}")
    public String questionDetail(Model model,@PathVariable("qid")int qid){
        Question question = questionService.selectById(qid);
        model.addAttribute("question",question);
//        model.addAttribute("user",userService.getUser(question.getUserId()));
        List<Comment>list = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject>comments = new ArrayList<>();
        for (Comment comment : list){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUser(question.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);
        return "detail";
    }
}
