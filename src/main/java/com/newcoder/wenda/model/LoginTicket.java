package com.newcoder.wenda.model;

import sun.rmi.runtime.Log;

import java.util.Date;

/**
 * Created by apple on 2017/8/27.
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;
    public LoginTicket(){}

    public LoginTicket(int id, int userId, Date expired, int status, String ticket) {
        this.id = id;
        this.userId = userId;
        this.expired = expired;
        this.status = status;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
