package com.newcoder.wenda.dao;

import com.newcoder.wenda.WendaApplication;
import com.newcoder.wenda.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by apple on 2017/8/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WendaApplication.class)
public class QuestionDAOTest {
    @Autowired
    private QuestionDAO questionDAO;

    @Test
    public void addQuestion() throws Exception {
        Random rd = new Random();
        for (int i = 0;i<10;i++){
            Question question = new Question();
            question.setTitle("this is titile"+rd.nextInt(100));
            question.setContent("this is content"+rd.nextInt(100));
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreateDate(date);
            question.setUserId(rd.nextInt(10));
            question.setCommentCount(rd.nextInt(100));
            questionDAO.addQuestion(question);
        }
    }

    @Test
    public void selectLatestQuestions() throws Exception {
        List<Question> list = questionDAO.selectLatestQuestions(2,0,10);
        for (Question question:list)
           System.out.println(question.getContent());
    }

}