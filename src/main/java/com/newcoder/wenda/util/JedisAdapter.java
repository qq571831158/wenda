package com.newcoder.wenda.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newcoder.wenda.controller.CommentController;
import com.newcoder.wenda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/*
* List:双向列表，适用于最新列表，关注列表
* lpush,lpop,blpop,lindex,lindex,lrange,lrem,linsert,lset,rpush
* Set 适用于无序的集合，点赞点踩，抽奖，已读，共同好友，（求交，求并，求异）
* sdiff,smenbers,sinter,scard
* SortedSet:优先队列，排行榜，
* zadd,zscore,zrange,zcount,zrand,zrevrank
* Hash:对象属性，不定常属性数
* hset,hget,hgetAll,hexists,hkeys,hvals
* kv:单一数值，验证码，PV,缓存
* set
* setex
* incr
* */

/**
 * Created by apple on 2017/8/30.
 */
@Service
public class JedisAdapter implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    public static void print(int index,Object o){
        System.out.println(String.format("%d,%s",index,o));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.set("foo", "bar");
        print(1,jedis.get("foo"));
        jedis.rename("foo","newFoo");
        print(1,jedis.get("newFoo"));
        //超时  图片验证码
        jedis.setex("hello2",15,"world");
        jedis.set("num","1");
        print(2,jedis.get("num"));
        jedis.incr("num");
        print(2,jedis.get("num"));
        jedis.incrBy("num",100);
        print(2,jedis.get("num"));
        jedis.decr("num");
        print(2,jedis.get("num"));
        jedis.decrBy("num",20);
        print(2,jedis.get("num"));

        print(3,jedis.keys("*"));

        //list
        String listName = "list";
        for (int i = 0;i<10;i++){
//            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(4,jedis.lrange(listName,1,9));
        print(4,jedis.lrange(listName,5,9));
        print(5,jedis.llen(listName));
        print(6,jedis.lpop(listName));
        print(7,jedis.llen(listName));
        print(9,jedis.lindex(listName,3));
        print(10,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"a4","xxx"));
        print(10,jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE,"a4","bbb"));
        print(11,jedis.lrange(listName,0,20));


        //hash
        String userKey = "userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","188888888");
        print(12,jedis.hget(userKey,"name"));
        print(13,jedis.hgetAll(userKey));
        jedis.hdel(userKey,"phone");
        print(14,jedis.hgetAll(userKey));
        print(15,jedis.hexists(userKey,"age"));
        print(16,jedis.hexists(userKey,"email"));
        print(17,jedis.hkeys(userKey));
        print(18,jedis.hvals(userKey));

        jedis.hsetnx(userKey,"school","zju");
        jedis.hsetnx(userKey,"name","yxy");
        print(19,jedis.hgetAll(userKey));

        //集合set
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for (int i = 0; i<10;i++){
            jedis.sadd(likeKey1,String.valueOf(i));
            jedis.sadd(likeKey2,String.valueOf(i*i));
        }
        print(20,jedis.smembers(likeKey1));
        print(21,jedis.smembers(likeKey2));
        print(22,jedis.sunion(likeKey1,likeKey2));
        print(23,jedis.sdiff(likeKey1,likeKey2));
        print(24,jedis.sinter(likeKey1,likeKey2));
        print(25,jedis.sismember(likeKey1,"12"));
        print(26,jedis.sismember(likeKey2,"16"));
        jedis.srem(likeKey1,"5");
        print(27,jedis.smembers(likeKey1));
        jedis.smove(likeKey2,likeKey1,"25");
        print(28,jedis.smembers(likeKey1));
        print(29,jedis.scard(likeKey1));
        print(30,jedis.srandmember(likeKey1,2));


        //集合 排行榜  优先队列  Sortedset
        String rankKey = "randKey";
        jedis.zadd(rankKey,15,"jim");
        jedis.zadd(rankKey,60,"ben");
        jedis.zadd(rankKey,75,"lucy");
        jedis.zadd(rankKey,80,"lee");
        jedis.zadd(rankKey,81,"Mei");
        print(31,jedis.zcount(rankKey,61,100));
        print(32,jedis.zscore(rankKey,"lucy"));
        jedis.zincrby(rankKey,2,"lucy");
        print(33,jedis.zscore(rankKey,"luc"));
        jedis.zincrby(rankKey,2,"luc");
        print(35,jedis.zrange(rankKey,0,100));
        print(36,jedis.zrange(rankKey,0,10));
        print(36,jedis.zrevrange(rankKey,1,3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey,60,100)){
            print(37,tuple.getElement()+":"+tuple.getScore());
        }

        print(38,jedis.zrank(rankKey,"ben"));
        print(39,jedis.zrevrank(rankKey,"ben"));

        String setKey = "zset";
        jedis.zadd(setKey,1,"a");
        jedis.zadd(setKey,1,"b");
        jedis.zadd(setKey,1,"c");
        jedis.zadd(setKey,1,"d");
        jedis.zadd(setKey,1,"e");
        print(40,jedis.zlexcount(setKey,"-","+"));
        print(41,jedis.zlexcount(setKey,"(b","[d"));
        jedis.zrem(setKey,"b");
        print(43,jedis.zrange(setKey,0,10));
        jedis.zremrangeByLex(setKey,"(c","+");
        print(44,jedis.zrange(setKey,0,2));

        JedisPool pool = new JedisPool();
//        for (int i = 0;i<100;i++){
//            Jedis j = pool.getResource();
//            print(45,j.get("foo"));
//            j.close();
//        }

        User user = new User();
        user.setName("xx");
        user.setPassword("xxx");
        user.setHeadUrl("http://dasd");;
        user.setSalt("sdas");;
        user.setId(1);;
        jedis.set("user1", JSONObject.toJSONString(user));
        print(46,jedis.get("user1"));
        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value,User.class);
        print(47,user2.getName());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("发送异常"+e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("发送异常"+e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("发送异常"+e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("发送异常"+e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return false;
    }
}
