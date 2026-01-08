package com.chatapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatapp.model.Message;
import com.chatapp.repository.MessageRepository;

@Service
public class ChatService {

    private final MessageRepository repository;

    public ChatService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message save(Message message) {
        return repository.save(message);
    }

    public List<Message> getPublicMessages() {
        return repository.findByReceiver("all");
    }

    public List<Message> getPrivateMessages(String user1, String user2) {
        return repository.findPrivateChat(user1, user2);
    }

    public List<Message> getGroupMessages(String group) {
        return repository.findByReceiver(group);
    }
}
