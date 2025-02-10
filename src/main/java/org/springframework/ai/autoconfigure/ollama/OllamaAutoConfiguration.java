//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.springframework.ai.autoconfigure.ollama;

import com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeAutoConfiguration;
import com.mrsnow.ai.config.OllamaModelConfig;
import io.micrometer.observation.ObservationRegistry;
import java.util.List;
import java.util.Objects;
import org.springframework.ai.chat.observation.ChatModelObservationConvention;
import org.springframework.ai.embedding.observation.EmbeddingModelObservationConvention;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration(
        after = {RestClientAutoConfiguration.class, DashScopeAutoConfiguration.class}
)
@ConditionalOnClass({OllamaApi.class})
@EnableConfigurationProperties({OllamaChatProperties.class, OllamaEmbeddingProperties.class, OllamaConnectionProperties.class})
@ImportAutoConfiguration(
        classes = {RestClientAutoConfiguration.class, WebClientAutoConfiguration.class}
)
@ConditionalOnProperty(prefix = "mrsnow.model", name = "choice", havingValue = "ollama")
public class OllamaAutoConfiguration {
    public OllamaAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({OllamaConnectionDetails.class})
    public PropertiesOllamaConnectionDetails ollamaConnectionDetails(OllamaConnectionProperties properties) {
        return new PropertiesOllamaConnectionDetails(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public OllamaApi ollamaApi(OllamaConnectionDetails connectionDetails, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder) {
        return new OllamaApi(connectionDetails.getBaseUrl(), restClientBuilder, webClientBuilder);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.ollama.chat",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public OllamaChatModel ollamaChatModel(OllamaApi ollamaApi, OllamaChatProperties properties, List<FunctionCallback> toolFunctionCallbacks, FunctionCallbackContext functionCallbackContext, ObjectProvider<ObservationRegistry> observationRegistry, ObjectProvider<ChatModelObservationConvention> observationConvention) {
        OllamaChatModel chatModel = new OllamaChatModel(ollamaApi, properties.getOptions(), functionCallbackContext, toolFunctionCallbacks, (ObservationRegistry)observationRegistry.getIfUnique(() -> {
            return ObservationRegistry.NOOP;
        }));
        Objects.requireNonNull(chatModel);
        observationConvention.ifAvailable(chatModel::setObservationConvention);
        return chatModel;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.ollama.embedding",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public OllamaEmbeddingModel ollamaEmbeddingModel(OllamaApi ollamaApi, OllamaEmbeddingProperties properties, ObjectProvider<ObservationRegistry> observationRegistry, ObjectProvider<EmbeddingModelObservationConvention> observationConvention) {
        OllamaEmbeddingModel embeddingModel = new OllamaEmbeddingModel(ollamaApi, properties.getOptions(), (ObservationRegistry)observationRegistry.getIfUnique(() -> {
            return ObservationRegistry.NOOP;
        }));
        Objects.requireNonNull(embeddingModel);
        observationConvention.ifAvailable(embeddingModel::setObservationConvention);
        return embeddingModel;
    }

    @Bean
    @ConditionalOnMissingBean
    public FunctionCallbackContext springAiFunctionManager(ApplicationContext context) {
        FunctionCallbackContext manager = new FunctionCallbackContext();
        manager.setApplicationContext(context);
        return manager;
    }

    static class PropertiesOllamaConnectionDetails implements OllamaConnectionDetails {
        private final OllamaConnectionProperties properties;

        PropertiesOllamaConnectionDetails(OllamaConnectionProperties properties) {
            this.properties = properties;
        }

        public String getBaseUrl() {
            return this.properties.getBaseUrl();
        }
    }
}
