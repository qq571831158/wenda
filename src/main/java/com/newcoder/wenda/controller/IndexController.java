package com.newcoder.wenda.controller;

import com.newcoder.wenda.dao.QuestionDAO;
import com.newcoder.wenda.dao.UserDAO;
import com.newcoder.wenda.model.Question;
import com.newcoder.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Random;

/**
 * Created by apple on 2017/8/19.
 */
//@Controller
public class IndexController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private QuestionDAO questionDAO;


    @RequestMapping(value = "/")
    public String index(Model model){

        return "index";
    }

    @RequestMapping(value = "insertquestion")
    @ResponseBody
    public String insert(){
        return "insertquestionsuccess";
    }
}
