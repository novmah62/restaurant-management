package com.novmah.restaurantmanagement.controller;

import com.itextpdf.text.DocumentException;
import com.novmah.restaurantmanagement.dto.request.OrderRequest;
import com.novmah.restaurantmanagement.dto.response.ApiResponse;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.status.OrderStatus;
import com.novmah.restaurantmanagement.entity.status.PaymentStatus;
import com.novmah.restaurantmanagement.service.OrderService;
import com.novmah.restaurantmanagement.service.PdfGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final PdfGeneratorService pdfGeneratorService;

    public OrderController(OrderService orderService, PdfGeneratorService pdfGeneratorService) {
        this.orderService = orderService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PatchMapping("/{id}/status/{orderStatus}")
    public OrderResponse updateOrderStatus(@PathVariable String id, @PathVariable OrderStatus orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus);
    }

    @PatchMapping("/{id}/paymentStatus/{paymentStatus}")
    public OrderResponse updatePaymentStatus(@PathVariable String id, @PathVariable PaymentStatus paymentStatus) {
        return orderService.updatePaymentStatus(id, paymentStatus);
    }

    @DeleteMapping("/{id}")
    public String deleteOrderById(@PathVariable String id) {
        return orderService.deleteOrderById(id);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponse> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/status/{orderStatus}")
    public List<OrderResponse> getOrderByStatus(@PathVariable OrderStatus orderStatus) {
        return orderService.getOrderByStatus(orderStatus);
    }

    @GetMapping("/paymentStatus/{paymentStatus}")
    public List<OrderResponse> getOrderByPaymentStatus(@PathVariable PaymentStatus paymentStatus) {
        return orderService.getOrderByPaymentStatus(paymentStatus);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrderByUserId(@PathVariable String userId) {
        return orderService.getOrderByUserId(userId);
    }

    @GetMapping("/{id}/invoice")
    public ApiResponse<String> generateInvoice(@PathVariable String id) throws DocumentException, IOException {
        return new ApiResponse<>(true, "Generate invoice", pdfGeneratorService.generateInvoice(id));
    }

}
