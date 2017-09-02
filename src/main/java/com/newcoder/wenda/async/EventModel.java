package com.newcoder.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2017/9/1.
 */
public class EventModel {
    //事件类型，谁触发的
    private EventType type;
    //触发者
    private int actorId;
    //触发载体
    private int entityType;
    //载体id
    private int entityId;

    private int entitiOnwerId;

    private Map<String,String>exts = new HashMap<>();

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntitiOnwerId() {
        return entitiOnwerId;
    }

    public EventModel setEntitiOnwerId(int entitiOnwerId) {
        this.entitiOnwerId = entitiOnwerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
    public EventModel setExt(String key,String value){
        exts.put(key,value);
        return this;
    }

    public String getExt(String key){
        return exts.get(key);
    }

    public  EventModel(EventType type){
        this.type = type;
    }

    public EventModel(){}

}
