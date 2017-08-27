package com.newcoder.wenda.controller;

import com.newcoder.wenda.model.HostHolder;
import com.newcoder.wenda.model.Question;
import com.newcoder.wenda.model.ViewObject;
import com.newcoder.wenda.service.QuestionService;
import com.newcoder.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/8/19.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;



    @RequestMapping(path = "user/{userId}",method = RequestMethod.GET)
    public String index(Model model, @PathVariable("userId")int userId){
        List<ViewObject> vos = getQuestion(userId,0,10);
        model.addAttribute("vos",vos);
        return "index";
    }

    @RequestMapping(path = {"/","/index"})
    public String index(Model model){
        List<ViewObject> vos = getQuestion(0,0,10);
        model.addAttribute("vos",vos);
        return "index";
    }

    private List<ViewObject> getQuestion(int userid,int offset,int limit){
        List<ViewObject> vos = new ArrayList<>();
        List<Question> questionList = questionService.getLatestQuestions(userid,offset,limit);
        for (Question question:questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
