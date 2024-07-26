package com.novmah.restaurantmanagement.service;

import com.itextpdf.text.DocumentException;
import com.novmah.restaurantmanagement.dto.response.ApiResponse;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PdfGeneratorService {

    ApiResponse generateInvoice(String orderId) throws DocumentException, IOException;

}
