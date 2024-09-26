package com.example.demo.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void registerChatMessages(String sender, String message) throws SQLException {
        messageRepository.registerChatMessages(sender, message);
    }
}
