package com.mushr00m.utils.mushr00mUtils;

import redis.clients.jedis.Jedis;

public class RedisKey {


    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    //获得redis锁
    public static Boolean getLock(String method,Jedis j){
        String result = j.set(method,"1",SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,5000);
        if(result.equals(LOCK_SUCCESS)){
            return true;
        }
        return false;
    }

    //释放redis锁
    public static void throwLock(String method,Jedis j){
        j.del(method);
    }
}
