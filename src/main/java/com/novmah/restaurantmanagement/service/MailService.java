package com.novmah.restaurantmanagement.service;


import com.novmah.restaurantmanagement.dto.response.EmailDetails;

public interface MailService {

    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);

}
