package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.dto.response.EmailDetails;
import com.novmah.restaurantmanagement.dto.response.OrderMessage;
import com.novmah.restaurantmanagement.entity.Role;
import com.novmah.restaurantmanagement.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class NotificationServiceImpl {

    private final MailService mailService;

    public NotificationServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = "notification.queue")
    public void handleOrderMessage(OrderMessage orderMessage) {
        log.info("order consumer: {}", orderMessage);

        Set<Role> roles = orderMessage.getUserRole();
        for (Role role : roles) {
            switch (role.getName()) {
                case "ROLE_USER" -> mailService.sendEmailAlert(EmailDetails.builder()
                        .recipient(orderMessage.getUserEmail())
                        .subject("Order Confirmation")
                        .messageBody(createCustomerEmailContent(orderMessage)).build());
                case "ROLE_CHEF" -> mailService.sendEmailAlert(EmailDetails.builder()
                        .recipient("manh06022003@gmail.com")
                        .subject("New Order Details")
                        .messageBody(createKitchenEmailContent(orderMessage)).build());
                default ->
                    log.info("error send message");
            }
        }

    }

    public String createCustomerEmailContent(OrderMessage orderMessage) {
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(orderMessage.getUserName()).append(",\n\n");
        content.append("Thank you for your order! Here are your order details:\n");
        content.append("Items:\n");
        for (OrderItemDto item : orderMessage.getOrderItemDtoList()) {
            content.append("- ").append(item.getFoodDto().getName()).append(" x ")
                    .append(item.getQuantity()).append(" @ ")
                    .append("\n");
        }
        content.append("\nTotal Amount: ").append(orderMessage.getTotalPrice()).append("\n\n");
        content.append("Thank you for shopping with us!\n");
        content.append("Best regards,\n");
        content.append("Restaurant");
        return content.toString();
    }

    public String createKitchenEmailContent(OrderMessage orderMessage) {
        StringBuilder content = new StringBuilder();
        content.append("New order\n\n");
        content.append("Items to prepare:\n");
        for (OrderItemDto item : orderMessage.getOrderItemDtoList()) {
            content.append("- ").append(item.getFoodDto().getName()).append(" x ")
                    .append(item.getQuantity()).append("\n");
        }
        return content.toString();
    }
}
