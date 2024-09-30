package com.example.demo.Utility;

import com.example.demo.Messages.Message;
import com.example.demo.Messages.MessageService;
import com.example.demo.Users.UserRepository;
import com.example.demo.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private Set<String> connectedUsers = new HashSet<>();

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Map<String, Object> send(@RequestBody Map<String, Object> payload) throws Exception {
        String messageContent = (String) payload.get("message");
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        String jwtToken = (String) payload.get("jwtToken");
        String emailAddress = userService.jwtTokenToEmail(jwtToken);
        String username = userService.emailToUsername(emailAddress);

        messageService.registerChatMessages(username, messageContent);

        Map<String, Object> response = new HashMap<>();
        response.put("time", time);
        response.put("sender", username);
        response.put("content", messageContent);

        return response;
    }


    @Scheduled(fixedRate = 1000)
    public void sendCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = dateTime.format(formatter);
        messagingTemplate.convertAndSend("/topic/time", data);
    }

    @Scheduled(fixedRate = 1000)
    public void bannedAccounts() throws SQLException {
        messagingTemplate.convertAndSend("/topic/banned", "Total accounts banned: " + userService.numberOfBannedUsers());
    }

    @MessageMapping("/join")
    @SendTo("/topic/onlinelist")
    public void addUser(String username) {
        connectedUsers.add(username);
        messagingTemplate.convertAndSend("/topic/onlinelist", connectedUsers);
//        messagingTemplate.convertAndSend("/topic/messages", username + " has joined the chat.");
    }

    @MessageMapping("leave")
    @SendTo("/topic/onlinelist")
    public void removeUser(String username) {
        connectedUsers.remove(username);
        messagingTemplate.convertAndSend("/topic/onlinelist", connectedUsers);
//        messagingTemplate.convertAndSend("/topic/messages", username + " has left the chat.");
    }

}
