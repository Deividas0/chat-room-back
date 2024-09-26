package com.example.demo.Users;

import com.example.demo.Utility.JwtDecoder;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Repository;

import java.sql.*;

import static com.example.demo.Utility.Constants.*;

@Repository
public class UserRepository {

    private static Connection _connection;

    public static void sqlConnection() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Registration of new user.
    public void newUser(String username, String emailAddress, String password) throws SQLException {
        final String sql = "INSERT into users (username, email_address, password) values (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, emailAddress);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();
        }
    }

    // Check if email already exists in database ( if exists return true, else false )
    public boolean checkIfEmailExists(String emailAddress) throws SQLException {
        final String sql = "SELECT email_address FROM users WHERE email_address = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If email already exist in the database return "true"
                return true;
            } else {
                // If email doesn't exist in the database return "false"
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Check if username already exists in database ( if exists return true, else false )
    public boolean checkIfUsernameExists(String username) throws SQLException {
        final String sql = "SELECT username FROM users WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If username already exist in the database return "true"
                return true;
            } else {
                // If username doesn't exist in the database return "false"
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if there’s an SQL exception
            throw e; // Rethrow the exception after logging it
        }
    }
    // Log in
    public int login(String emailAddress, String password) throws SQLException {
        final String sql = "SELECT * FROM users WHERE email_address = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, emailAddress);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (resultSet.wasNull()) {
                    return 0;
                } else {
                    return id;
                }
            }
        }
        return 0;
    }

    // Forgot password
    public String forgotPassword(String emailAddress) throws SQLException {
        final String sql = "SELECT password FROM users WHERE email_address = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, emailAddress);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                if (resultSet.wasNull()) {
                    return "";
                } else {
                    return password;
                }
            }
        }
        return "";
    }

    // Check how many users are banned
    public int numberOfBannedUsers() throws SQLException {
        final String sql = "SELECT COUNT(*) AS total_banned_users \n" +
                "FROM users \n" +
                "WHERE restriction_end_date IS NOT NULL;";
        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total_banned_users");
            }
        }
        return 0; // Return 0 if there are no results
    }

    // Giving email receiving username
    public String emailToUsername(String emailAddress) throws SQLException {
        final String sql = "SELECT username FROM users WHERE email_address = ?";
        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, emailAddress);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        }
        return "";
    }

    // Giving jwtToken receiving email
    public String jwtTokenToEmail(String jwtToken){
        Claims claims = JwtDecoder.decodeJwt(jwtToken);
        return claims.get("Email").toString();
    }

}
