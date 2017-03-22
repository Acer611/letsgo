package com.umessage.letsgo.core.utils;


import com.umessage.letsgo.core.exception.OperateUploadFileException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.*;
import java.util.Random;

/**
* @version V1.0  
*/
public class ExcelOperateUtil {

	private static ExcelOperateUtil excelOperateUtil = null;
	//单类模式创建工具类实类
	public static ExcelOperateUtil instance(){
		if (excelOperateUtil==null) {
			excelOperateUtil = new ExcelOperateUtil();
		}
		return excelOperateUtil;
	}
	
	private ExcelOperateUtil() {
		super();
	}

	//得到WORKBOOK对象
	public Workbook generateWordBook(){
		//创建一个EXCEL文件
		return new HSSFWorkbook();
	}
	
	//在workbook里面创建一个sheet并得到SHEET对象
	public Sheet generateSheet(Workbook workbook, String sheetName){
		//创建一个sheet对象
		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);
		Sheet sheet = workbook.createSheet(safeSheetName);
		return sheet;
	}
	//在sheet里面创建一个row并得到row对象
	//rowIndex 起始值为0
	public Row generateRow(Sheet sheet, int rowIndex){
		//创建一个row对象
		Row row = sheet.createRow(rowIndex);
		return row;
	}
	//在Row里面创建一个cell并得到cell对象
	//cellIndex 起始值为0
	public Cell generateCell(Row row, int cellIndex){
		//创建一个cell对象
		Cell cell = row.createCell(cellIndex);
		return cell;
	}
	//在Row里面创建一个指定样式的cell并得到cell对象
	//cellIndex 起始值为0
	public Cell generateCell(Row row, int cellIndex, CellStyle cellStyle){
		//创建一个cell对象
		Cell cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyle);
		return cell;
	}
	
	//将workbook写到硬盘文件上
	public String writeExcel(Workbook workbook, String fileName){
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			return fileName;
		} catch (FileNotFoundException e) {
			throw new OperateUploadFileException(e);
		} catch (IOException e) {
			throw new OperateUploadFileException(e);
		}
		
	}
//--------------------[write data into the excel file]---End--------------------//
	
	
	
//--------------------[read data from the excel file]----Begin--------------------//
	//从硬盘文件上读取excel文件并返回workbook对象
	public Workbook readFromExcel(String fileUrl){
		try {
			InputStream inputStream = new FileInputStream(fileUrl);
			Workbook workbook = WorkbookFactory.create(inputStream);
			inputStream.close();
			return workbook;
		} catch (Exception e) {
			throw new OperateUploadFileException(e);
		}
	}
	//从workbook对象读取指定下标的sheet对象
	public Sheet readFromWorkbook(Workbook workbook, int sheetIndex){
		try {
			return workbook.getSheetAt(sheetIndex);
		} catch (Exception e) {
			throw new OperateUploadFileException(e);
		}
	}
	//从sheet对象读取指定下标的row对象
	public Row readFromSheet(Sheet sheet, int rowIndex){
		try {
			return sheet.getRow(rowIndex);
		} catch (Exception e) {
			throw new OperateUploadFileException(e);
		}
	}
	//从row对象读取指定下标的cell对象
	public Cell readFromRow(Row row, int cellIndex){
		try {
			return row.getCell(cellIndex);
		} catch (Exception e) {
			throw new OperateUploadFileException(e);
		}
	}
//--------------------[read data from the excel file]----End--------------------//

}
