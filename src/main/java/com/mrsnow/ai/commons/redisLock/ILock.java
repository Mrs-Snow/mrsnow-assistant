package com.mrsnow.ai.commons.redisLock;


public interface ILock {
    void setExpire(String name,int expire);
    boolean tryLock(String name);
    void unlock(String name);
}
