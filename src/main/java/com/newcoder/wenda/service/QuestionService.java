package com.newcoder.wenda.service;

import com.newcoder.wenda.dao.QuestionDAO;
import com.newcoder.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by apple on 2017/8/19.
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId,int offset,int limit){
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }

    public int addQuestion(Question question){
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0 ;
    }

    public Question selectById(int id){
        return questionDAO.selectById(id);
    }

}
