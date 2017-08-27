package com.newcoder.wenda.model;

import org.springframework.stereotype.Component;

/**
 * Created by apple on 2017/8/27.
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }


}
