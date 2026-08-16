package com.nkpro.springaihelloworld.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatAPI")
public class MultiModelChatController {

    private final ChatClient openAIChatClient;
    private final ChatClient awsBedRockChatClient;

    public MultiModelChatController(@Qualifier("openAiChatClient") ChatClient openAIChatClient,
                                    @Qualifier("awsChatClient") ChatClient awsBedRockChatClient) {
        this.openAIChatClient = openAIChatClient;
        this.awsBedRockChatClient = awsBedRockChatClient;
    }

    @GetMapping("/openAI/chat")
    public String chatOpenAI(@RequestParam("message") String message) {
        return openAIChatClient
                .prompt(message)
                .call()
                .content();
    }

    @GetMapping("/awsAI/chat")
    public String chatAWSBedRock(@RequestParam("message") String message) {
        return awsBedRockChatClient
                .prompt(message)
                .call()
                .content();
    }

}
