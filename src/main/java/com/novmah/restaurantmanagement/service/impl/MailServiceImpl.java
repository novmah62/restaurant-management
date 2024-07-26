package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.dto.response.EmailDetails;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Async
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(senderEmail);
            messageHelper.setTo(emailDetails.getRecipient());
            messageHelper.setSubject(emailDetails.getSubject());
            messageHelper.setText(emailDetails.getMessageBody());
        };
        try {
            mailSender.send(message);
            log.info("Mail send successfully");
        } catch (MailException e) {
            log.error("Failed to send mail: ", e);
            throw new ResourceNotFoundException("Exception occurred when sending mail to " + emailDetails.getRecipient());
        }
    }

    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            messageHelper.setFrom(senderEmail);
            messageHelper.setTo(emailDetails.getRecipient());
            messageHelper.setSubject(emailDetails.getSubject());
            messageHelper.setText(emailDetails.getMessageBody());
            messageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        };
        try {
            mailSender.send(message);
            log.info("Mail send successfully");
        } catch (MailException e) {
            log.error("Failed to send mail: ", e);
            throw new ResourceNotFoundException("Exception occurred when sending mail to " + emailDetails.getRecipient());
        }

    }
}
