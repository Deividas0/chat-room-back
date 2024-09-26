package com.example.demo.Messages;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.demo.Utility.Constants.*;

@Repository
public class MessageRepository {
    private static Connection _connection;

    public static void sqlConnection() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void registerChatMessages(String sender, String message) throws SQLException {
        final String sql = "INSERT INTO messages (sender, message) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, message);

            preparedStatement.executeUpdate();
        }
    }
}
