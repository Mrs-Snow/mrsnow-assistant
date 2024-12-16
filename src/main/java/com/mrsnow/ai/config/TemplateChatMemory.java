package com.mrsnow.ai.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  13:17
 **/
@Configuration
public class TemplateChatMemory implements ChatMemory {
    private Map<String,List<Message>> memory = new HashMap<>();
    @Override
    public void add(String conversationId, List<Message> messages) {
        memory.put(conversationId,messages);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<Message> messages = memory.get(conversationId);
        if (messages == null){
            return new ArrayList<>();
        }
        return messages;
    }

    @Override
    public void clear(String conversationId) {
        memory.clear();
    }
}
