package com.example.demo.Messages;

public class Message {
    private int id;
    private String sender;
    private String message;
    private String messageDate;

    public Message(int id, String sender, String message, String messageDate) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.messageDate = messageDate;
    }

    public Message(String sender, String message, String messageDate) {
        this.sender = sender;
        this.message = message;
        this.messageDate = messageDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
}
