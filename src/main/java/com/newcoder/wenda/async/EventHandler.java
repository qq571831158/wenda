package com.newcoder.wenda.async;

import java.util.List;

/**
 * Created by apple on 2017/9/1.
 */
public interface EventHandler {

    //映射
    void doHandler(EventModel model);
    //注册，
    List<EventType> getSupportEventTypes();
}
