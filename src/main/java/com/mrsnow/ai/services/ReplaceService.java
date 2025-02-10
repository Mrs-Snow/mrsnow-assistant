package com.mrsnow.ai.services;

import com.mrsnow.ai.data.RouteData;
import com.mrsnow.ai.data.RspType;
import com.mrsnow.ai.feign.SystemApi;
import com.mrsnow.ai.tools.SearchTools;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReplaceService {

    private final RouteData db;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private SystemApi systemApi;
    private Long tenantId;

    public ReplaceService(){
        db = new RouteData();
//        initData();
    }

    public void initRouteData(Long id){
        Map<String, String> routes = systemApi.getRoutes(tenantId);
        db.setRoutes(routes);
    }
    private void initData(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("员工维护","/user/employee");
        hashMap.put("组织机构","/user/org");
        hashMap.put("岗位维护","/user/position");
        hashMap.put("租户维护","/tenant/tenant");
        db.setRoutes(hashMap);
    }

    public void replaceTo(String talkId,String name){
        String r = Optional.ofNullable(db.getRoutes().get(name)).orElseThrow(() -> new RuntimeException("未找到此功能"));
        redisTemplate.opsForValue().set(talkId,new SearchTools.Route(r,RspType.REPLACE));
    }

}
