package com.example.demo.Users;

import com.example.demo.Emails.EmailsRepository;
import com.example.demo.Utility.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    private ResponseEntity<String> newUser(@RequestBody User user) throws SQLException {
        if (userService.checkIfUsernameExists(user.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        if (userService.checkIfEmailExists(user.getEmailAddress())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } else {
            userService.newUser(user.getUsername(), user.getEmailAddress(), user.getPassword());
            String email2 = user.getEmailAddress();
            EmailsRepository.registrationEmail(user.getUsername(), email2);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) throws SQLException {
        String username = userService.emailToUsername(user.getEmailAddress());
        int id = userService.login(user.getEmailAddress(), user.getPassword());

        if (id == 0) {
            return new ResponseEntity<>(Map.of("error", "Invalid username or password"), HttpStatus.BAD_REQUEST);
        }

        String token = JwtGenerator.generateJwt(user.getUsername(), user.getEmailAddress(), id);

        // Create a map to hold both the token and the username
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody User user) throws SQLException {
        if (userService.checkIfEmailExists(user.getEmailAddress())) {
            String password = userService.forgotPassword(user.getEmailAddress());
            String email2 = user.getEmailAddress();
            EmailsRepository.forgotPasswordEmail(password,email2);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email address", HttpStatus.BAD_REQUEST);
        }
    }
}
