package com.newcoder.wenda.async;

/**
 * Created by apple on 2017/8/30.
 */
public enum  EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
    EventType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
