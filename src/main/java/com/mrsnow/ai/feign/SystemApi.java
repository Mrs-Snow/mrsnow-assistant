package com.mrsnow.ai.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-17  16:06
 **/
@FeignClient(name = "lamp-oauth-server", path = "/anyone")
public interface SystemApi {
    @GetMapping("/getRoutes")
    Map<String,String> getRoutes(Long tenantId);
}
