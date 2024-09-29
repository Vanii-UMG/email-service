package com.service.email.controller;

import com.service.email.dto.response.EmailResponse;
import com.service.email.service.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public List<EmailResponse> getAllEmails(){
        return emailService.getAllEmail();
    }

    @PostMapping
    public String processEmail(@RequestParam("file") MultipartFile file) throws IOException {
        emailService.processEmail(file);
        return "Emails saved successfully";
    }

}
