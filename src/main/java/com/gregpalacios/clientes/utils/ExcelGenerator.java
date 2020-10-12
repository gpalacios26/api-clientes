package com.gregpalacios.clientes.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gregpalacios.clientes.entities.Cliente;

public class ExcelGenerator {

	public static ByteArrayInputStream usuariosToExcel(List<Cliente> clientes) throws IOException {
	    String[] COLUMNs = {"Código", "Nombres", "Apellidos", "Correo"};
	    try(
	        Workbook workbook = new XSSFWorkbook();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ){
		      Sheet sheet = workbook.createSheet("Clientes");
		   
		      Font headerFont = workbook.createFont();
		      headerFont.setBold(true);
		      headerFont.setColor(IndexedColors.BLUE.getIndex());
		   
		      CellStyle headerCellStyle = workbook.createCellStyle();
		      headerCellStyle.setFont(headerFont);
		   
		      // Row for Header
		      Row headerRow = sheet.createRow(0);
		   
		      // Header
		      for (int col = 0; col < COLUMNs.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(COLUMNs[col]);
		        cell.setCellStyle(headerCellStyle);
		      }
		   
		      int rowIdx = 1;
		      for (Cliente cliente : clientes) {
		        Row row = sheet.createRow(rowIdx++);
		   
		        row.createCell(0).setCellValue(cliente.getId());
		        row.createCell(1).setCellValue(cliente.getName());
		        row.createCell(2).setCellValue(cliente.getSurname());
		        row.createCell(3).setCellValue(cliente.getEmail());
		      }
		      
			  // Resize all columns to fit the content size
		      for(int i = 0; i < COLUMNs.length; i++) {
		        sheet.autoSizeColumn(i);
		      }
		   
		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    }
	  }
}
