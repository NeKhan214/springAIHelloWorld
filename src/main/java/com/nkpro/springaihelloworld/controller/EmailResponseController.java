package com.nkpro.springaihelloworld.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailResponseController {

    @Value("classpath:/promptTemplates/userEmailPrompt.st")
    private Resource EMAIL_PROMPT_MESSAGE;
    private final ChatClient chatClient;
    private static final String SYSTEM_MESSAGE = """
            You are customer service assistant. Help user to draft a support email response to 
            increase customer support team productivity.
            """;


    EmailResponseController(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/response")
    public String emailResponse(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage) {
        return chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(EMAIL_PROMPT_MESSAGE)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage))
                .system(SYSTEM_MESSAGE)
                .call()
                .content();
    }


}
