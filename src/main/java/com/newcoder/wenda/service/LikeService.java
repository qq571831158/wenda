package com.newcoder.wenda.service;

import com.newcoder.wenda.util.JedisAdapter;
import com.newcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by apple on 2017/8/30.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        if (jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        return jedisAdapter.sismember(disLikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId,int entityType,int entitId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entitId);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entitId);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId,int entityType,int entitId){
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entitId);
        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entitId);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }


}
