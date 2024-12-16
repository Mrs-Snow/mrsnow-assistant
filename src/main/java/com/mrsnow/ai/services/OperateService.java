package com.mrsnow.ai.services;

import com.mrsnow.ai.data.RspType;
import com.mrsnow.ai.tools.SearchTools;
import com.mrsnow.ai.web.bo.OperateBo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  12:52
 **/
@Service
public class OperateService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public OperateBo getOperate(String key){
        try {
            SearchTools.Route r = (SearchTools.Route) redisTemplate.opsForValue().get(key);
            if (r==null){
                return new OperateBo(null, RspType.NOTHING);
            }
            return new OperateBo(r.route(), r.type());
        } finally {
            redisTemplate.delete(key);
        }
    }
}
