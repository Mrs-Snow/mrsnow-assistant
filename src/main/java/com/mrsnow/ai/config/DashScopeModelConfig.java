package com.mrsnow.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Author dongzhen
 * @CreateTime: 2025-02-10  10:59
 **/
@Configuration
@ConditionalOnProperty(prefix = "mrsnow.model", name = "choice", havingValue = "dashScope",matchIfMissing = true)
public class DashScopeModelConfig {

}
