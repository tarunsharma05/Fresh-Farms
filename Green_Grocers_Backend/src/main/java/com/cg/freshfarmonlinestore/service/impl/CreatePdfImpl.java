package com.cg.freshfarmonlinestore.service.impl;

import java.io.ByteArrayOutputStream;


import java.io.FileOutputStream;
 
import org.springframework.stereotype.Service;
 
import com.cg.freshfarmonlinestore.entity.Order;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
/**
* Service implementation for converting HTML to PDF.
*/
@Service
public class CreatePdfImpl {
	/**
     * Converts HTML content to PDF format.
     *
     * @param processedHtml The processed HTML content to convert.
     * @return The path of the generated PDF file.
     */
public String htmlToPdf(String processedHtml) {
		Order order= new Order();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
			DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setFontProvider(defaultFont);
			HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
			// Change the file path according to your requirements
			
			//FileOutputStream fout = new FileOutputStream("C:/Users/VANKHAND/OneDrive - Capgemini/Desktop/pdf/sample.pdf");
			String pdfFilePath = "C:/Users/AMAHADIK/OneDrive - Capgemini/Desktop/Project-Pdf/" + order.getOrderId() + ".pdf";
			 FileOutputStream fout = new FileOutputStream(pdfFilePath);
			byteArrayOutputStream.writeTo(fout);
			byteArrayOutputStream.close();
			byteArrayOutputStream.flush();
			fout.close();
			return null;
		} catch(Exception ex) {
			// Handle exceptions here
            ex.printStackTrace(); // Print the exception trace for debugging
		}
		return null;
	}
}