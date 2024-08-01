package com.novmah.restaurantmanagement.service;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PdfGeneratorService {

    String generateInvoice(String orderId) throws DocumentException, IOException;

}
