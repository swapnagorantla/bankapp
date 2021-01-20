package com.grokonez.jwtauthentication.security.services;

import com.grokonez.jwtauthentication.model.Transaction;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TransPdfExporter {
    private List<Transaction> listTransaction;

    public TransPdfExporter(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }


    private void writeTableHeader(PdfPTable table) {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Account Number", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction Timestamp", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table)
    {
        PdfPCell cell1 =new PdfPCell();
        cell1.setBackgroundColor(Color.white);
        cell1.setPadding(5);
        for (Transaction transaction : listTransaction) {
            table.addCell(String.valueOf(transaction.getAccountNumber()));
            table.addCell(transaction.getTransactionAmount().toString());
            table.addCell(String.valueOf(transaction.getTransactionDateTime().getTime()));
        }
        table.addCell(cell1);
        
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException
    {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);


        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
