package com.mrsnow.ai.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-11  15:19
 **/
public interface ChatClientService {
    @GetMapping("/chat/{msg}")
    String chat(@PathVariable String msg);
}
