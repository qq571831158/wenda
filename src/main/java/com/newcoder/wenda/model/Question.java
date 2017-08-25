package com.newcoder.wenda.model;

import java.util.Date;

/**
 * Created by apple on 2017/8/19.
 */
public class Question {

    private int id;
    private String title;
    private String content;
    private Date createDate;
    private int userId;
    private int commentCount;

    public Question(int id, String title, String content, Date createDate, int userId, int commentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.userId = userId;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Question(){}

}
