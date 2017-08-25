package com.newcoder.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2017/8/19.
 */
public class ViewObject {
    private Map<String,Object> map  =  new HashMap<>();

    public void set(String key,Object value){
        map.put(key,value);
    }

    public Object get(String key){
        return map.get(key);
    }
}
