package com.example.demo.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.Utility.Constants.*;

@Repository
public class MessageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Connection _connection;

    public static void sqlConnection() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void registerChatMessages(String sender, String message) throws SQLException {
        final String sql = "INSERT INTO messages (sender, message) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, message);

            preparedStatement.executeUpdate();
        }
    }

    // ChatGPT
    public List<Map<String, Object>> getLatestMessages() throws SQLException {
        List<Map<String, Object>> messages = new ArrayList<>();
        final String sql = "SELECT sender, message AS content, DATE_FORMAT(message_date, '%H:%i') as time FROM messages ORDER BY message_date DESC LIMIT 50";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> messageMap = new HashMap<>();
                    messageMap.put("sender", resultSet.getString("sender"));
                    messageMap.put("content", resultSet.getString("content"));
                    messageMap.put("time", resultSet.getString("time"));

                    messages.add(messageMap);
                }
            }
        }
        return messages;
    }
}


