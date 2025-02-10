package com.mrsnow.ai.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Author dongzhen
 * @CreateTime: 2025-02-10  10:57
 **/
@Configuration
@ConditionalOnProperty(prefix = "mrsnow.model", name = "choice", havingValue = "ollama")
public class OllamaModelConfig {
}
