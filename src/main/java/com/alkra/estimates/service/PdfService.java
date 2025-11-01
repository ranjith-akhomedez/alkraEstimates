package com.alkra.estimates.service;

import com.alkra.estimates.dto.EstimateResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class PdfService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public byte[] generateEstimatePdf(EstimateResponse estimate) {
        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Add title
            Paragraph title = new Paragraph("ALKRA ESTIMATES", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Add estimate details
            Paragraph subtitle = new Paragraph("Estimate #" + estimate.getId(), HEADER_FONT);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Customer information (if available)
            if (estimate.getCustomerName() != null) {
                document.add(new Paragraph("Customer Information", HEADER_FONT));
                document.add(new Paragraph("Name: " + estimate.getCustomerName(), NORMAL_FONT));
                if (estimate.getCustomerEmail() != null) {
                    document.add(new Paragraph("Email: " + estimate.getCustomerEmail(), NORMAL_FONT));
                }
                if (estimate.getCustomerPhone() != null) {
                    document.add(new Paragraph("Phone: " + estimate.getCustomerPhone(), NORMAL_FONT));
                }
                document.add(new Paragraph(" "));
            }

            // Product details table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            addTableHeader(table, "Product Type:", estimate.getProductTypeName());
            addTableHeader(table, "Height (ft):", String.valueOf(estimate.getHeight()));
            addTableHeader(table, "Width (ft):", String.valueOf(estimate.getWidth()));
            
            if (estimate.getNumberOfLeafs() != null) {
                addTableHeader(table, "Number of Leafs:", String.valueOf(estimate.getNumberOfLeafs()));
            }
            
            if (estimate.getGlassThickness() != null) {
                addTableHeader(table, "Glass Thickness (mm):", String.valueOf(estimate.getGlassThickness()));
            }

            addTableHeader(table, "Area (sq ft):", String.valueOf(estimate.getAreaInSquareFeet()));
            addTableHeader(table, "Rate per sq ft:", "₹ " + estimate.getRatePerSquareFeet());
            
            // Add total cost with emphasis
            PdfPCell costLabelCell = new PdfPCell(new Phrase("Total Cost:", HEADER_FONT));
            costLabelCell.setBorder(Rectangle.NO_BORDER);
            costLabelCell.setPadding(5);
            costLabelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(costLabelCell);

            PdfPCell costValueCell = new PdfPCell(new Phrase("₹ " + estimate.getTotalCost(), HEADER_FONT));
            costValueCell.setBorder(Rectangle.NO_BORDER);
            costValueCell.setPadding(5);
            costValueCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(costValueCell);

            document.add(table);

            // Add rough drawing
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Rough Measurements Diagram", HEADER_FONT));
            document.add(new Paragraph(" "));

            // Create a simple rectangular diagram
            PdfPTable diagramTable = new PdfPTable(1);
            diagramTable.setWidthPercentage(70);
            diagramTable.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell diagramCell = new PdfPCell();
            diagramCell.setFixedHeight(200);
            diagramCell.setPadding(20);
            
            Paragraph diagramContent = new Paragraph();
            diagramContent.add(new Chunk("Width: " + estimate.getWidth() + " ft\n\n", NORMAL_FONT));
            diagramContent.add(new Chunk("┌" + "─".repeat(30) + "┐\n", NORMAL_FONT));
            diagramContent.add(new Chunk("│" + " ".repeat(30) + "│\n", NORMAL_FONT));
            diagramContent.add(new Chunk("│  Height: " + estimate.getHeight() + " ft" + " ".repeat(11) + "│\n", NORMAL_FONT));
            diagramContent.add(new Chunk("│" + " ".repeat(30) + "│\n", NORMAL_FONT));
            diagramContent.add(new Chunk("└" + "─".repeat(30) + "┘\n", NORMAL_FONT));
            
            if (estimate.getNumberOfLeafs() != null && estimate.getNumberOfLeafs() > 1) {
                diagramContent.add(new Chunk("\nLeafs: " + estimate.getNumberOfLeafs(), NORMAL_FONT));
            }
            
            diagramCell.addElement(diagramContent);
            diagramTable.addCell(diagramCell);
            document.add(diagramTable);

            // Add footer
            document.add(new Paragraph(" "));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            Paragraph footer = new Paragraph("Generated on: " + 
                    estimate.getCreatedAt().format(formatter), NORMAL_FONT);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            return outputStream.toByteArray();
        } catch (DocumentException e) {
            log.error("Error generating PDF", e);
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, HEADER_FONT));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }
}
