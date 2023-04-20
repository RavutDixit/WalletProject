package com.wallet.WalletProject.Entity;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Transaction> listTransactions;
	
	public UserExcelExporter(List<Transaction> listTransactions) {
		super();
		this.listTransactions = listTransactions;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Transactions");
	}

	private void writeHeaderRow()
	{
		Row row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("Transaction ID");
		
	    cell = row.createCell(1);
		cell.setCellValue("Transaction Date");
		
	    cell = row.createCell(2);
		cell.setCellValue("Amount");
		
		cell = row.createCell(3);
		cell.setCellValue("To Wallet ID");
		
		cell = row.createCell(4);
		cell.setCellValue("From Wallet ID");
		
		cell = row.createCell(5);
		cell.setCellValue("Debit");
		
		cell = row.createCell(6);
		cell.setCellValue("Credit");
		
	}
	
	private void writeDataRows()
	{
		int rowCount = 1;
		CellStyle cellStyle= workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		for(Transaction transaction: listTransactions)
		{
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(transaction.getTransactionId());
			sheet.autoSizeColumn(0);
			
		    cell = row.createCell(1);
			cell.setCellValue(transaction.getTransactionDate());
			cell.setCellStyle(cellStyle);
			sheet.autoSizeColumn(1);
			
			cell = row.createCell(2);
			cell.setCellValue(transaction.getAmount());
			sheet.autoSizeColumn(2);
			
			cell = row.createCell(3);
			cell.setCellValue(transaction.getToWallet());
			sheet.autoSizeColumn(3);
			
			cell = row.createCell(4);
			cell.setCellValue(transaction.getFromWallet());
			sheet.autoSizeColumn(4);
			
			cell = row.createCell(5);
			cell.setCellValue(transaction.getDebit());
			sheet.autoSizeColumn(5);
			
			cell = row.createCell(6);
			cell.setCellValue(transaction.getCredit());
			sheet.autoSizeColumn(6);
			
		}
		
	}
	
	public void export(HttpServletResponse response) throws IOException
	{
		writeHeaderRow();
		writeDataRows();
		
		 ServletOutputStream outputStream = response.getOutputStream();
		 workbook.write(outputStream);
		 workbook.close();
		 outputStream.close();
	}
}
