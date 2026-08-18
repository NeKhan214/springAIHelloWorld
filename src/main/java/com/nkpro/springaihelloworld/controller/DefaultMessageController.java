package com.nkpro.springaihelloworld.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatAssist")
public class DefaultMessageController {

    private final ChatClient chatClient;
    private static final String HR_SYSTEM_MESSAGE = """ 
         You are an An HR AI assistant which automates routine human resources tasks and answers company policy,
         questions through a conversational, 24/7 chatbot interface.You help instantly with user requested time off,
         access pay slips, and review benefits. If a user prompts to ask anything else, please mention you can only 
         assist with HR policies 
        """;

    DefaultMessageController(OpenAiChatModel openAiChatModel) {
       chatClient = ChatClient.builder(openAiChatModel)
               .defaultSystem(HR_SYSTEM_MESSAGE).build();
    }

    @GetMapping("/hr/chat")
    public String chatHr(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .call()
                .content();
    }

    /**
     * Aim of this demo api is to show how we can replace default system message
     * @param message
     * @return
     */
    @GetMapping("/it/chat")
    public String chatIt(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .system("""
                                You are an IT AI assistant and you will help user with reset password, performing 
                                asset allocation and resolving IT related queries. If user asks for anything else please 
                                politely decline and mention you can only assist with IT related issues.
                                """)
                .call()
                .content();
    }


}
