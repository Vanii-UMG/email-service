package com.service.email.service;

import com.service.email.dto.response.EmailResponse;
import com.service.email.mapper.EmailMapper;
import com.service.email.model.EmailModel;
import com.service.email.repository.EmailRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService {

    private final Logger logger = Logger.getLogger(EmailService.class.getName());
    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository, EmailMapper emailMapper, RequestContextFilter requestContextFilter) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    public void processEmail(MultipartFile file) throws IOException {
        // Expresión regular para detectar correos electrónicos
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        // Leer el archivo usando BufferedReader
        List<String> emails = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Separar la línea por espacios o comas
                String[] words = line.split("\\s+|,");
                for (String word : words) {
                    Matcher matcher = pattern.matcher(word);
                    if (matcher.matches() && !emails.contains(word)) {
                        // Si la palabra coincide con la expresión regular, es un correo válido
                        emails.add(word);
                    }
                }
            }
        }

        // Guardar los correos válidos en la base de datos
        saveEmails(emails);
    }

    public List<EmailResponse> getAllEmail() {
        return emailRepository.findAll().stream()
                .map(emailMapper::asResponse)
                .toList();
    }

    private void saveEmails(List<String> emails) {
        for (String email : emails) {
            EmailModel emailModel = new EmailModel();
            emailModel.setEmail(email);
            emailRepository.save(emailModel);
        }
    }

}
