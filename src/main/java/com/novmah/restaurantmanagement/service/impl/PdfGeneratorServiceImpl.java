package com.novmah.restaurantmanagement.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.dto.response.EmailDetails;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.Order;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.mapper.OrderMapper;
import com.novmah.restaurantmanagement.repository.OrderRepository;
import com.novmah.restaurantmanagement.service.MailService;
import com.novmah.restaurantmanagement.service.PdfGeneratorService;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private static final String FILE = "C:\\SharedStatements\\order_invoice.pdf";

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MailService mailService;

    public PdfGeneratorServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, MailService mailService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.mailService = mailService;
    }

    @Override
    public String generateInvoice(String orderId) throws IOException, DocumentException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderId));
        designInvoice(orderMapper.map(order));
        mailService.sendEmailWithAttachment(EmailDetails.builder()
                .recipient(order.getUser().getEmail())
                .subject("INVOICE OF ORDER")
                .messageBody("Kindly find your requested invoice attached!")
                .attachment(FILE)
                .build());
        return "Invoice send successfully";
    }

    public static void designInvoice(OrderResponse orderResponse) throws IOException, DocumentException {
        Rectangle pageSize = new Rectangle(PageSize.A4);
        Document document = new Document(pageSize);

        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        PdfPTable restaurantInfoTable = new PdfPTable(1);
        PdfPCell restaurantName = new PdfPCell(new Phrase("RESTAURANT", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
        restaurantName.setBackgroundColor(BaseColor.LIGHT_GRAY);
        restaurantName.setPadding(10f);
        restaurantName.setHorizontalAlignment(Element.ALIGN_CENTER);
        restaurantInfoTable.addCell(restaurantName);

        PdfPCell restaurantAddress = new PdfPCell(new Phrase("123 Street, City, Country", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
        restaurantAddress.setHorizontalAlignment(Element.ALIGN_CENTER);
        restaurantAddress.setBorder(Rectangle.NO_BORDER);
        restaurantInfoTable.addCell(restaurantAddress);

        document.add(restaurantInfoTable);

        PdfPTable orderInfoTable = new PdfPTable(2);
        orderInfoTable.setSpacingBefore(20f);
        orderInfoTable.setWidthPercentage(100);

        PdfPCell orderTitle = new PdfPCell(new Phrase("Order Invoice", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
        orderTitle.setColspan(2);
        orderTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderTitle.setBorder(Rectangle.NO_BORDER);
        orderTitle.setPaddingBottom(10f);
        orderInfoTable.addCell(orderTitle);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        orderInfoTable.addCell(getCell("Order Date: " + orderResponse.getCreatedAt().format(formatter), PdfPCell.ALIGN_LEFT));
        orderInfoTable.addCell(getCell("Customer Name: " + orderResponse.getUserName(), PdfPCell.ALIGN_RIGHT));
        orderInfoTable.addCell(getCell("Table Number: " + orderResponse.getTableNumber(), PdfPCell.ALIGN_LEFT));
        orderInfoTable.addCell(getCell("Payment Method: " + orderResponse.getPaymentMethod(), PdfPCell.ALIGN_RIGHT));
        orderInfoTable.addCell(getCell("Payment Status: " + orderResponse.getPaymentStatus(), PdfPCell.ALIGN_LEFT));

        document.add(orderInfoTable);

        PdfPTable orderItemsTable = new PdfPTable(5); // 5 cột: ID, Food, Quantity, Status, Price
        orderItemsTable.setSpacingBefore(20f);
        orderItemsTable.setWidthPercentage(100);

        addTableHeader(orderItemsTable, new String[]{"ID", "Food", "Quantity", "Status", "Price"});

        for (OrderItemDto item : orderResponse.getOrderItemDtoList()) {
            orderItemsTable.addCell(new Phrase(item.getId().toString()));
            orderItemsTable.addCell(new Phrase(item.getFoodDto().getName()));
            orderItemsTable.addCell(new Phrase(String.valueOf(item.getQuantity())));
            orderItemsTable.addCell(new Phrase(item.getStatus().toString()));
            orderItemsTable.addCell(new Phrase(item.getFoodDto().getPrice().toString()));
        }

        document.add(orderItemsTable);

        PdfPTable totalPriceTable = new PdfPTable(2);
        totalPriceTable.setSpacingBefore(20f);
        totalPriceTable.setWidthPercentage(100);

        PdfPCell totalLabel = new PdfPCell(new Phrase("Total Price", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setBorder(Rectangle.NO_BORDER);
        totalLabel.setPaddingRight(10f);

        PdfPCell totalValue = new PdfPCell(new Phrase(orderResponse.getTotalPrice().toString(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
        totalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalValue.setBorder(Rectangle.NO_BORDER);

        totalPriceTable.addCell(totalLabel);
        totalPriceTable.addCell(totalValue);

        document.add(totalPriceTable);

        document.close();
        outputStream.close();
    }

    private static PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
        cell.setPadding(5f);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static void addTableHeader(PdfPTable table, String[] headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5f);
            table.addCell(headerCell);
        }
    }
}
