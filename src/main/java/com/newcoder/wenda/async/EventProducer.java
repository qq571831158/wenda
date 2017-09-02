package com.newcoder.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.wenda.util.JedisAdapter;
import com.newcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by apple on 2017/9/1.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
//            BlockingQueue<EventModel> queue = new ArrayBlockingQueue<EventModel>();
//            jedisAdapter
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueue();
            jedisAdapter.lpush(key,json);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
