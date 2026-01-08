package com.chatapp.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.model.Message;
import com.chatapp.service.ChatService;

@Controller
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    private Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/addUser")
    public void addUser(@Payload String username) {
        onlineUsers.add(username);
        messagingTemplate.convertAndSend("/topic/users", onlineUsers);
    }

    @MessageMapping("/chat")
    public void sendPublicMessage(@Payload Message message) {

        Message fixedMessage = new Message();
        fixedMessage.setSender(message.getSender());
        fixedMessage.setReceiver("all");
        fixedMessage.setContent(message.getContent());

        chatService.save(fixedMessage);

        messagingTemplate.convertAndSend("/topic/messages", fixedMessage);
    }

    @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload Message message) {
        chatService.save(message);

        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );
    }

    @GetMapping("/history/public")
    public List<Message> getPublicHistory() {
        return chatService.getPublicMessages();
    }
}
