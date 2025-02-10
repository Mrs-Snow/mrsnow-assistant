//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.ai.autoconfigure.dashscope;

import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAudioTranscriptionApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeSpeechSynthesisApi;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.rerank.DashScopeRerankModel;
import com.alibaba.dashscope.audio.asr.transcription.Transcription;
import com.alibaba.dashscope.audio.tts.SpeechSynthesizer;
import com.mrsnow.ai.config.DashScopeModelConfig;
import com.mrsnow.ai.config.OllamaModelConfig;
import io.micrometer.observation.ObservationRegistry;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.autoconfigure.retry.SpringAiRetryAutoConfiguration;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@ConditionalOnClass({DashScopeApi.class, DashScopeModelConfig.class})
@AutoConfiguration(
        after = {RestClientAutoConfiguration.class, WebClientAutoConfiguration.class, SpringAiRetryAutoConfiguration.class}
)
@EnableConfigurationProperties({DashScopeConnectionProperties.class, DashScopeChatProperties.class, DashScopeImageProperties.class, DashScopeSpeechSynthesisProperties.class, DashScopeAudioTranscriptionProperties.class, DashScopeEmbeddingProperties.class, DashScopeRerankProperties.class})
@ImportAutoConfiguration(
        classes = {SpringAiRetryAutoConfiguration.class, RestClientAutoConfiguration.class, WebClientAutoConfiguration.class}
)
@ConditionalOnProperty(prefix = "mrsnow.model", name = "choice", havingValue = "dashscope", matchIfMissing = true)
public class DashScopeAutoConfiguration {
    public DashScopeAutoConfiguration() {
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public SpeechSynthesizer speechSynthesizer() {
        return new SpeechSynthesizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public Transcription transcription() {
        return new Transcription();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.chat",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeChatModel dashscopeChatModel(DashScopeConnectionProperties commonProperties, DashScopeChatProperties chatProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, List<FunctionCallback> toolFunctionCallbacks, FunctionCallbackContext functionCallbackContext, RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler, ObjectProvider<ObservationRegistry> observationRegistry) {
        if (!CollectionUtils.isEmpty(toolFunctionCallbacks)) {
            chatProperties.getOptions().getFunctionCallbacks().addAll(toolFunctionCallbacks);
        }

        DashScopeApi dashscopeApi = this.dashscopeChatApi(commonProperties, chatProperties, restClientBuilder, webClientBuilder, responseErrorHandler);
        return new DashScopeChatModel(dashscopeApi, chatProperties.getOptions(), functionCallbackContext, retryTemplate, (ObservationRegistry)observationRegistry.getIfUnique(() -> {
            return ObservationRegistry.NOOP;
        }));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.embedding",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeApi dashscopeChatApi(DashScopeConnectionProperties commonProperties, DashScopeChatProperties chatProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, ResponseErrorHandler responseErrorHandler) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, chatProperties, "chat");
        return new DashScopeApi(resolved.baseUrl(), resolved.apiKey(), resolved.workspaceId(), restClientBuilder, webClientBuilder, responseErrorHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.embedding",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeEmbeddingModel dashscopeEmbeddingModel(DashScopeConnectionProperties commonProperties, DashScopeEmbeddingProperties embeddingProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler) {
        DashScopeApi dashScopeApi = this.dashscopeEmbeddingApi(commonProperties, embeddingProperties, restClientBuilder, webClientBuilder, responseErrorHandler);
        return new DashScopeEmbeddingModel(dashScopeApi, embeddingProperties.getMetadataMode(), embeddingProperties.getOptions(), retryTemplate);
    }

    public DashScopeApi dashscopeEmbeddingApi(DashScopeConnectionProperties commonProperties, DashScopeEmbeddingProperties embeddingProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, ResponseErrorHandler responseErrorHandler) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, embeddingProperties, "embedding");
        return new DashScopeApi(resolved.baseUrl(), resolved.apiKey(), resolved.workspaceId(), restClientBuilder, webClientBuilder, responseErrorHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.audio.synthesis",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeSpeechSynthesisApi dashScopeSpeechSynthesisApi(DashScopeConnectionProperties commonProperties, DashScopeSpeechSynthesisProperties speechSynthesisProperties) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, speechSynthesisProperties, "speechsynthesis");
        return new DashScopeSpeechSynthesisApi(resolved.apiKey());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.audio.transcription",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeAudioTranscriptionApi dashScopeAudioTranscriptionApi(DashScopeConnectionProperties commonProperties, DashScopeAudioTranscriptionProperties audioTranscriptionProperties) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, audioTranscriptionProperties, "audiotranscription");
        return new DashScopeAudioTranscriptionApi(resolved.apiKey());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.embedding",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeAgentApi dashscopeAgentApi(DashScopeConnectionProperties commonProperties, DashScopeChatProperties chatProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, ResponseErrorHandler responseErrorHandler) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, chatProperties, "chat");
        return new DashScopeAgentApi(resolved.baseUrl(), resolved.apiKey(), resolved.workspaceId(), restClientBuilder, webClientBuilder, responseErrorHandler);
    }

    @Bean
    public RestClientCustomizer restClientCustomizer(DashScopeConnectionProperties commonProperties) {
        return (restClientBuilder) -> {
            restClientBuilder.requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS.withReadTimeout(Duration.ofSeconds((long)commonProperties.getReadTimeout()))));
        };
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.image",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeImageModel dashScopeImageModel(DashScopeConnectionProperties commonProperties, DashScopeImageProperties imageProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, imageProperties, "image");
        DashScopeImageApi dashScopeImageApi = new DashScopeImageApi(resolved.baseUrl(), resolved.apiKey(), resolved.workspaceId(), restClientBuilder, webClientBuilder, responseErrorHandler);
        return new DashScopeImageModel(dashScopeImageApi, imageProperties.getOptions(), retryTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.rerank",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeRerankModel dashscopeRerankModel(DashScopeConnectionProperties commonProperties, DashScopeRerankProperties rerankProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler) {
        ResolvedConnectionProperties resolved = resolveConnectionProperties(commonProperties, rerankProperties, "rerank");
        DashScopeApi dashscopeApi = new DashScopeApi(resolved.baseUrl(), resolved.apiKey(), resolved.workspaceId(), restClientBuilder, webClientBuilder, responseErrorHandler);
        return new DashScopeRerankModel(dashscopeApi, rerankProperties.getOptions(), retryTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.audio.synthesis",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeSpeechSynthesisModel dashScopeSpeechSynthesisModel(DashScopeConnectionProperties commonProperties, DashScopeSpeechSynthesisProperties speechSynthesisProperties, RetryTemplate retryTemplate) {
        resolveConnectionProperties(commonProperties, speechSynthesisProperties, "speechsynthesis");
        DashScopeSpeechSynthesisApi dashScopeSpeechSynthesisApi = this.dashScopeSpeechSynthesisApi(commonProperties, speechSynthesisProperties);
        return new DashScopeSpeechSynthesisModel(dashScopeSpeechSynthesisApi, speechSynthesisProperties.getOptions(), retryTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "spring.ai.dashscope.audio.transcription",
            name = {"enabled"},
            havingValue = "true",
            matchIfMissing = true
    )
    public DashScopeAudioTranscriptionModel dashScopeAudioTranscriptionModel(DashScopeConnectionProperties commonProperties, DashScopeAudioTranscriptionProperties audioTranscriptionProperties, RetryTemplate retryTemplate) {
        resolveConnectionProperties(commonProperties, audioTranscriptionProperties, "audiotranscription");
        DashScopeAudioTranscriptionApi dashScopeSpeechSynthesisApi = this.dashScopeAudioTranscriptionApi(commonProperties, audioTranscriptionProperties);
        return new DashScopeAudioTranscriptionModel(dashScopeSpeechSynthesisApi, audioTranscriptionProperties.getOptions(), retryTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public FunctionCallbackContext springAiFunctionManager(ApplicationContext context) {
        FunctionCallbackContext manager = new FunctionCallbackContext();
        manager.setApplicationContext(context);
        return manager;
    }

    @NotNull
    private static ResolvedConnectionProperties resolveConnectionProperties(DashScopeParentProperties commonProperties, DashScopeParentProperties modelProperties, String modelType) {
        String baseUrl = StringUtils.hasText(modelProperties.getBaseUrl()) ? modelProperties.getBaseUrl() : commonProperties.getBaseUrl();
        String apiKey = StringUtils.hasText(modelProperties.getApiKey()) ? modelProperties.getApiKey() : commonProperties.getApiKey();
        String workspaceId = StringUtils.hasText(modelProperties.getWorkspaceId()) ? modelProperties.getWorkspaceId() : commonProperties.getWorkspaceId();
        Map<String, List<String>> connectionHeaders = new HashMap();
        if (StringUtils.hasText(workspaceId)) {
            connectionHeaders.put("DashScope-Workspace", List.of(workspaceId));
        }

        if (Objects.isNull(apiKey) && Objects.nonNull(System.getenv("AI_DASHSCOPE_API_KEY"))) {
            apiKey = System.getenv("AI_DASHSCOPE_API_KEY");
        }

        Assert.hasText(baseUrl, "DashScope base URL must be set.  Use the connection property: spring.ai.dashscope.base-url or spring.ai.dashscope." + modelType + ".base-url property.");
        Assert.hasText(apiKey, "DashScope API key must be set. Use the connection property: spring.ai.dashscope.api-key or spring.ai.dashscope." + modelType + ".api-key property.");
        return new ResolvedConnectionProperties(baseUrl, apiKey, workspaceId, CollectionUtils.toMultiValueMap(connectionHeaders));
    }

    private static record ResolvedConnectionProperties(String baseUrl, String apiKey, String workspaceId, MultiValueMap<String, String> headers) {
        private ResolvedConnectionProperties(String baseUrl, String apiKey, String workspaceId, MultiValueMap<String, String> headers) {
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
            this.workspaceId = workspaceId;
            this.headers = headers;
        }

        public String baseUrl() {
            return this.baseUrl;
        }

        public String apiKey() {
            return this.apiKey;
        }

        public String workspaceId() {
            return this.workspaceId;
        }

        public MultiValueMap<String, String> headers() {
            return this.headers;
        }
    }
}
