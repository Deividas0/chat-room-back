package com.example.demo.Users;

import com.example.demo.Emails.EmailsRepository;
import com.example.demo.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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
    public ResponseEntity<String> login(@RequestBody User user) throws SQLException {
        int id = userService.login(user.getEmailAddress(), user.getPassword());
        return (id == 0) ? new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(JwtGenerator.generateJwt(user.getUsername(), user.getEmailAddress(), id), HttpStatus.OK);
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
