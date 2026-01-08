package com.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chatapp.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver(String receiver);

    @Query("SELECT m FROM Message m WHERE " +
           "(m.sender = :user1 AND m.receiver = :user2) OR " +
           "(m.sender = :user2 AND m.receiver = :user1)")
    List<Message> findPrivateChat(String user1, String user2);
}
