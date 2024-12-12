package com.mrsnow.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class QwenAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(QwenAssistantApplication.class, args);
    }

}
