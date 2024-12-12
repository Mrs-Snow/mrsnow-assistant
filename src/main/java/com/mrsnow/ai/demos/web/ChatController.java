/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mrsnow.ai.demos.web;

import com.mrsnow.ai.demos.web.bo.ChatBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

@RestController
@RequestMapping("/ai")
@Slf4j
public class ChatController {

	private final ChatClient chatClient;

	public ChatController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}

	@PostMapping("/chat")
	public String chat(@RequestBody ChatBo chatBo) {
		log.info("收到请求：{}",chatBo.getMessage());
		return this.chatClient.prompt().user(chatBo.getMessage()).call().content();
	}

	@GetMapping(value = "/stream",produces = "text/event-stream")
	public Flux<String> stream(String message) {
		log.info("收到请求：{}",message);
//		Flux<String> stringFlux = Flux.just("你好", "我是助手阿震", "请问有什么可以帮助您的")
//				.delayElements(Duration.ofMillis(500));
		Flux<String> content = this.chatClient.prompt().user(message).stream().content();
		return content.concatWith(Flux.just("[END]")); // 添加结束标识
	}

}
