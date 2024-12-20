package com.mrsnow.ai.services;

import com.mrsnow.ai.data.RouteData;
import com.mrsnow.ai.data.RspType;
import com.mrsnow.ai.feign.SystemApi;
import com.mrsnow.ai.tools.SearchTools;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  10:26
 * 路由跳转
 **/
@Service
@RequiredArgsConstructor
public class ReplaceService {

    private final RouteData db;
    private final SystemApi systemApi;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public void initRouteData(Long tenantId){
        Map<String, String> routes = systemApi.getRoutes(tenantId);
        db.setRoutes(routes);
    }

    public void replaceTo(String talkId,String name){
        String r = Optional.ofNullable(db.getRoutes().get(name)).orElseThrow(() -> new RuntimeException("未找到此功能"));
        redisTemplate.opsForValue().set(talkId,new SearchTools.Route(r,RspType.REPLACE));
    }




}
