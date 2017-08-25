package com.newcoder.wenda.controller;

import com.newcoder.wenda.model.Question;
import com.newcoder.wenda.model.ViewObject;
import com.newcoder.wenda.service.QuestionService;
import com.newcoder.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/8/19.
 */
@Controller
public class HomeController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;


    @RequestMapping(path = {"/","/index"})
    public String index(Model model, HttpSession session){
        List<Question> questionList = questionService.getLatestQuestions(0,0,10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question:questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "index";
    }
}
