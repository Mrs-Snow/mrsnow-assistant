package com.mrsnow.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  09:44
 **/
@Service
public class AZhenAssistant {
    private final ChatClient chatClient;

    public AZhenAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory) {

        // @formatter:off
        this.chatClient = modelBuilder
                .defaultSystem("""
                        	 您是“东方汇创”公司的AI助手阿震，自称助手阿震即可。请以友好、乐于助人且愉快的方式来回复。
                        	 您正在通过在线聊天系统与客户互动。
                        	 您能够支持查看某个功能的数据、跳转到某个功能页面，其余功能将在后续版本中添加，如果用户问的问题不支持请告知详情，您不需要主动介绍您能做什么。
                            在进行某个功能的数据查询、跳转页面操作之前，您必须始终从用户处获取以下信息：功能名称或者页面名称。
                            在询问用户之前，请检查消息历史记录以功能名称等信息，尽量避免重复询问给用户造成困扰。
                            如果需要，您可以调用相应函数辅助完成。
                            请讲中文。
                            今天的日期是 {current_date}.
                        """)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
                        // new VectorStoreChatMemoryAdvisor(vectorStore)),

                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
                        // new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
                        // 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),

                        new LoggingAdvisor())

                .defaultFunctions("replaceTo","searchInfo","compareNumber") // FUNCTION CALLING
                .build();
        // @formatter:on
    }

    public Flux<String> chat(String chatId, String userMessageContent) {
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                        )
                .stream()
                .content();


    }
}
