package com.example.demo.Users;

public class User {
    private int id;
    private String username;
    private String emailAddress;
    private String password;
    private String registrationDate;

    public User(int id, String username, String emailAddress, String password, String registrationDate) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public User(String username, String emailAddress, String password, String registrationDate) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public User(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
