package com.mrsnow.ai.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-17  16:06
 * 此接口可以通过参数配置化来动态获取服务名称和path
 **/
@FeignClient(name = "lamp-oauth-server", path = "/anyone")
public interface SystemApi {
    /**
     * 获得路由信息
     * @param tenantId
     * @return
     */
    @GetMapping("/getRoutes")
    Map<String,String> getRoutes(Long tenantId);
}
