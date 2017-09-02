package com.newcoder.wenda.service;

import com.newcoder.wenda.dao.CommentDAO;
import com.newcoder.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by apple on 2017/8/29.
 */
@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;
    public List<Comment>getCommentsByEntity(int entityId,int entityType){
        return commentDAO.selectCommentByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDAO.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }

    public boolean deleteComment(int commentId){
        return commentDAO.updateStatus(commentId,1) > 0 ;
    }

    public Comment getCommentById(int id){
        return commentDAO.selectById(id);
    }

}
