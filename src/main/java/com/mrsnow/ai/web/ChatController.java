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
package com.mrsnow.ai.web;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrsnow.ai.services.AZhenAssistant;
import com.mrsnow.ai.services.OperateService;
import com.mrsnow.ai.web.bo.ChatBo;
import com.mrsnow.ai.web.bo.RspBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;

@RestController
@RequestMapping("/ai")
@Slf4j
public class ChatController {

    private final AZhenAssistant agent;
    @Autowired
    private OperateService operateService;

    public ChatController(AZhenAssistant agent) {
        this.agent = agent;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(String talkId, String message) {
        log.info("收到请求：{}", message);
        return this.agent.chat(talkId, message);
    }

    @PostMapping("/operate")
    public RspBo operate(@RequestBody ChatBo chatBo) {
        return RspBo.success(operateService.getOperate(chatBo.getTalkId()));
    }
}
