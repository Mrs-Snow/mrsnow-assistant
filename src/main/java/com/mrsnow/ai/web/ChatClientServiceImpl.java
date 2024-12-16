package com.mrsnow.ai.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-11  15:21
 **/
@RestController
@Slf4j
@RequestMapping("ai-service")
public class ChatClientServiceImpl implements ChatClientService{
    private final ChatClient chatClient;
    public ChatClientServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String chat(String msg) {
        log.info("收到消息：{}",msg);
        long l1 = System.currentTimeMillis();
        String content = this.chatClient.prompt().user(msg).call().content();
        long l2 = System.currentTimeMillis();
        log.info("本次耗时{}ms,AI回答：{}",l2-l1,content);
        return content;
    }
}
