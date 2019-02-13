package com.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ReadExcel {

	@SuppressWarnings("resource")
	public static List<List<String>> read(InputStream is) throws IOException {  
	       List<List<String>> allRows = new ArrayList<List<String>>();  
	       Workbook wb = null;  
	       try {  
	           wb = WorkbookFactory.create(is);  
	           Sheet sheet = wb.getSheetAt(0);  
	           int maxRowNum = sheet.getLastRowNum();  
	           int minRowNum = sheet.getFirstRowNum();  
	  
	           // 跳过头，从第二行开始读取  
	           for (int i = minRowNum; i <= maxRowNum; i++) {  
	               Row row = sheet.getRow(i);  
	               if (row == null) {  
	                   continue;  
	               }  
	               List<String> rowData = readLine(row);  
	               allRows.add(rowData);  
	           }  
	  
	       } catch (Exception e) { 
	    	   e.printStackTrace();
	           throw new IOException(e);  
	       } finally {  
	           if (is != null) {  
	               is.close();  
	           }  
	           if (wb != null && wb instanceof SXSSFWorkbook) {  
	               SXSSFWorkbook xssfwb = (SXSSFWorkbook) wb;  
	               xssfwb.dispose();  
	           }  
	       }  
	       return allRows;  
	}
   //读取每行数据  
   @SuppressWarnings("deprecation")
	private static List<String> readLine(Row row) {  
	       short minColNum = row.getFirstCellNum();  
	       short maxColNum = row.getLastCellNum();  
	       List<String> dataList = new ArrayList<String>();  
	       for (short colIndex = minColNum; colIndex < maxColNum; colIndex++) {  
	           Cell cell = row.getCell(colIndex);  
	           if (cell == null) {  
	               continue;  
	           }  
	           int cellType = cell.getCellType();  
	           Object value = null;  
	           if (Cell.CELL_TYPE_NUMERIC == cellType) {  
	               value = cell.getNumericCellValue();  
	           } else if (Cell.CELL_TYPE_STRING == cellType) {  
	               value = cell.getStringCellValue();  
	           } else {  
	               value = cell.getStringCellValue();  
	           }  
	           dataList.add(value.toString());  
	       }  
	  
	       return dataList;  
	   } 
   
//   // 去读Excel的方法readExcel，该方法的入口参数为一个File对象  
//   public static List<List<String>> readExcel(InputStream is) {  
//   	//创建容器
//       List<List<String>> list1 = new ArrayList<>();
//       try {  
//           // 创建输入流，读取Excel  
////           FileInputStream in = new FileInputStream(file);  
//           // jxl提供的Workbook类  
//           Workbook wb = Workbook.getWorkbook(is);  
//           // Excel的页签数量  
//           int sheet_size = wb.getNumberOfSheets();  
//           for (int index = 0; index < sheet_size; index++) {  
//               // 每个页签创建一个Sheet对象  
//               Sheet sheet = wb.getSheet(index); 
//               // sheet.getRows()返回该页的总行数  
//               for (int i = 0; i < sheet.getRows(); i++) {  
//                   // sheet.getColumns()返回该页的总列数  
//               	List<String> list0 = new ArrayList<>();
//                   for (int j = 0; j < sheet.getColumns(); j++) {  
//                       String cellinfo = sheet.getCell(j, i).getContents();  
//                       list0.add(cellinfo);
//                   }
//                   list1.add(list0);
//               }  
//               System.out.println(list1);
//           }  
//       } catch (BiffException e) {  
//           e.printStackTrace();  
//       } catch (IOException e) {  
//           e.printStackTrace();  
//       } 
//       return list1;
//   } 
}
