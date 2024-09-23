package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void newUser(String username, String emailAddress, String password) throws SQLException {
        userRepository.newUser(username, emailAddress, password);
    }

    public boolean checkIfEmailExists(String emailAddress) throws SQLException {
        return userRepository.checkIfEmailExists(emailAddress);
    }

    public boolean checkIfUsernameExists(String username) throws SQLException {
        return userRepository.checkIfUsernameExists(username);
    }
    public int login(String email, String password) throws SQLException {
        return userRepository.login(email, password);
    }
    public String forgotPassword(String emailAddress) throws SQLException {
        return userRepository.forgotPassword(emailAddress);
    }
}
