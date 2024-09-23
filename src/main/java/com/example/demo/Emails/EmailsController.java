package com.example.demo.Emails;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@RestController
@RequestMapping("/emails")
public class EmailsController {


}
