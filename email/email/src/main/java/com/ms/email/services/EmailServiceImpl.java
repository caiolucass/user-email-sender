package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService{

    final EmailRepository emailRepository;
    final JavaMailSender mailSender;

    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel){
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(emailModel.getEmailTo());
            simpleMailMessage.setSubject(emailModel.getSubject());
            simpleMailMessage.setText(emailModel.getText());
            mailSender.send(simpleMailMessage);
            emailModel.setStatusEmail(StatusEmail.SENT);

        }catch (MailException e){
          emailModel.setStatusEmail(StatusEmail.ERROR);

        }finally {
            return emailRepository.save(emailModel);
        }
    }
}
