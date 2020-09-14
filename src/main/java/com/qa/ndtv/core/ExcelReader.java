package com.qa.ndtv.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	/**
	 * Reads test data from excel sheet based on sheet name and returns two
	 * dimentional array.
	 * 
	 * @param path      Path
	 * @param sheetName SheetName
	 * @return Two dimentional Array
	 * @throws FileNotFoundException FileNotFoundException
	 * @throws IOException           IOException
	 */
	@SuppressWarnings("resource")
	public static Object[][] readExcel(String path, String sheetName) throws FileNotFoundException, IOException {
		List<Object[]> resultsData = new ArrayList<>();
		XSSFWorkbook _workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
		Sheet sheet = _workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.rowIterator();
		int i = 0;
		while (rowIterator.hasNext()) {
			List<Object> arrayExcelData = new ArrayList<>();
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();

			while (i != 0 && cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				CellType cellType = cell.getCellType();
				if (cellType == CellType.STRING) {
					arrayExcelData.add(cell.getStringCellValue());
				} else if (cellType == CellType.NUMERIC) {
					arrayExcelData.add(cell.getNumericCellValue());
				} else if (cellType == CellType.BOOLEAN) {
					arrayExcelData.add(cell.getBooleanCellValue());
				}
			}
			if (i > 0) {
				resultsData.add(arrayExcelData.toArray());
			}
			i++;
		}
		return resultsData.toArray(new Object[resultsData.size()][]);
	}
}
