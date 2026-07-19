package com.cg.freshfarmonlinestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.cg.freshfarmonlinestore.dto.OrderDto;
import com.cg.freshfarmonlinestore.mapper.PdfDataMapper;
import com.cg.freshfarmonlinestore.service.impl.CreatePdfImpl;
import com.cg.freshfarmonlinestore.service.impl.OrderServiceImpl;

/**
 * REST controller for generating PDF documents.
 */
@CrossOrigin("*")
@RestController
public class PdfController {

    @Autowired
    private CreatePdfImpl createPdfImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfDataMapper pdfDataMapper;

    /**
     * Endpoint to generate a PDF document for a specific order.
     * 
     * @param id The ID of the order for which to generate the PDF.
     * @return A string indicating the success of the operation.
     */
    @PostMapping(value = "/generate/document/{id}")
    public String generateDocument(@PathVariable("id") Long id) {
        // Fetch order details by ID
        OrderDto dto = orderServiceImpl.getOrder(id);

        // Map order data to the context for PDF generation
        org.thymeleaf.context.Context dataContext = pdfDataMapper.setData(dto);

        // Process the template with the context data
        String finalHtml = springTemplateEngine.process("template", dataContext);

        // Generate PDF from the processed HTML
        createPdfImpl.htmlToPdf(finalHtml);

        return "Success";
    }
}
