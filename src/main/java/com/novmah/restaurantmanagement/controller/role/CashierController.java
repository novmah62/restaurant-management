package com.novmah.restaurantmanagement.controller.role;

import com.itextpdf.text.DocumentException;
import com.novmah.restaurantmanagement.dto.response.ApiResponse;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.status.PaymentStatus;
import com.novmah.restaurantmanagement.service.OrderService;
import com.novmah.restaurantmanagement.service.PdfGeneratorService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cashier")
public class CashierController {
//    getOrderByPaymentStatus
//    updatePaymentStatus
//            getOrderById
//    getAllOrder
//            getOrderByUserId
//    generateInvoice

    private final OrderService orderService;
    private final PdfGeneratorService pdfGeneratorService;

    public CashierController(OrderService orderService, PdfGeneratorService pdfGeneratorService) {
        this.orderService = orderService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/order/paymentStatus/{paymentStatus}")
    public List<OrderResponse> getOrderByPaymentStatus(@PathVariable PaymentStatus paymentStatus) {
        return orderService.getOrderByPaymentStatus(paymentStatus);
    }

    @PatchMapping("/order/{id}/paymentStatus/{paymentStatus}")
    public OrderResponse updatePaymentStatus(@PathVariable String id, @PathVariable PaymentStatus paymentStatus) {
        return orderService.updatePaymentStatus(id, paymentStatus);
    }

    @GetMapping("/order/{id}")
    public OrderResponse getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponse> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/order/user/{userId}")
    public List<OrderResponse> getOrderByUserId(@PathVariable String userId) {
        return orderService.getOrderByUserId(userId);
    }

    @GetMapping("/order/{id}/invoice")
    public ApiResponse generateInvoice(@PathVariable String id) throws DocumentException, IOException {
        return pdfGeneratorService.generateInvoice(id);
    }


}
