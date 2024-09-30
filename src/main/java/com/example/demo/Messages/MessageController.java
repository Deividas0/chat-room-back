package com.example.demo.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @GetMapping("/latest")
    public List<Map<String, Object>> getLatestMessages() {
        try {
            return messageService.getLatestMessages();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Return an empty list if there's an error
        }
    }
}
