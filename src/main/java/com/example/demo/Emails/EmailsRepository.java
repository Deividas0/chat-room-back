package com.example.demo.Emails;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;

public class EmailsRepository {

    public static void registrationEmail(String username, String email2) {

        Email email = new Email();
        String emailType = "Registration";

        // Replace with your verified domain and valid email
        email.setFrom("Project Name", "mokslaivyksta@trial-3zxk54vkoqqljy6v.mlsender.net");
        email.addRecipient(username, email2);

        // Add multiple recipients
//        email.addRecipient("Second Recipient", "second@recipient.com");

        // Using a recipient object
//        Recipient recipient = new Recipient("Third Recipient", "third@recipient.com");
//        email.addRecipient(recipient.name, recipient.email);

        email.setSubject("Registration");
//        email.setPlain("Sveikinu");
        email.setHtml("<h1>You have successfully registered for the chat room</h1>");

        MailerSend ms = new MailerSend();
        ms.setToken("mlsn.f5dede72bba316b97c5df36bf03a0b4fad9d89b69c001ecccfec9e7720117c06");

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);

        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }
    public static void forgotPasswordEmail(String password, String email2) {

        Email email = new Email();
        String emailType = "Forgot password";

        // Replace with your verified domain and valid email
        email.setFrom("Project Name", "mokslaivyksta@trial-3zxk54vkoqqljy6v.mlsender.net");
        email.addRecipient("", email2);

        // Add multiple recipients
//        email.addRecipient("Second Recipient", "second@recipient.com");

        // Using a recipient object
//        Recipient recipient = new Recipient("Third Recipient", "third@recipient.com");
//        email.addRecipient(recipient.name, recipient.email);

        email.setSubject("Password reminder");
//        email.setPlain("Sveikinu");
        email.setHtml("<h1>Your password is: " + password + "</h1>");

        MailerSend ms = new MailerSend();
        ms.setToken("mlsn.f5dede72bba316b97c5df36bf03a0b4fad9d89b69c001ecccfec9e7720117c06");

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);

        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }
}