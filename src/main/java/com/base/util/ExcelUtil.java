package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {
	
	private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
	/**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    @SuppressWarnings("deprecation")
	public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
    
    /**
     * 保险表格导出
     * @param dataList
     * @param finalXlsxPath
     */
    public static void writeExcel(List<JSONObject> dataList, String finalXlsxPath){    	
    	OutputStream out = null;
        try {
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            HSSFWorkbook workBook = (HSSFWorkbook) getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            HSSFCellStyle style = workBook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
//            for (int i = 1; i <= rowNumber; i++) {
//                Row row = sheet.getRow(i);
//                sheet.removeRow(row);
//            }

            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
            	//创建容器
            	List<String> list = new LinkedList<String>();
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 2);
                // 得到要插入的每一条记录
                JSONObject jsonObject = dataList.get(j);
                //获取用户信息
                String guNo = jsonObject.getString("guNo");
                list.add(guNo);
                String guNameCn = jsonObject.getString("guNameCn");
                list.add(guNameCn);
                String guNameEn = jsonObject.getString("guNameEn");
                list.add(guNameEn);
                String idType = "护照";
                list.add(idType);
                String guPassportNum = jsonObject.getString("guPassportNum");
                list.add(guPassportNum);
                String guSexC = jsonObject.getString("guSexC");
                list.add(guSexC);
                String guBirthDate = jsonObject.getString("guBirthDate");
                list.add(guBirthDate);
                String gvTelephone = jsonObject.getString("gvTelephone");
                list.add(gvTelephone);
                String countryName = jsonObject.getString("countryName");
                list.add(countryName);
                String chufaTime = jsonObject.getString("gvEntryDate") + " " + jsonObject.getString("taketime");
                list.add(chufaTime);
                String gvArriveCity = jsonObject.getString("gvArriveCity");
                list.add(gvArriveCity);
                String gvCurrAddress = jsonObject.getString("gvCurrAddress");
                list.add(gvCurrAddress);
                String gvEntryDate = jsonObject.getString("gvEntryDate");
                list.add(gvEntryDate);
                String gvLeaveDate = jsonObject.getString("gvLeaveDate");
                list.add(gvLeaveDate);
                String gvFlightNum = jsonObject.getString("gvFlightNum");
                list.add(gvFlightNum);
                String gvPrintPlace = jsonObject.getString("gvPrintPlace");
                list.add(gvPrintPlace);
                list.add(gvEntryDate);
                
                System.out.println(list.size()+"------------");
                //写入表格
                for (int k = 0; k < list.size(); k++) {
	                // 在一行内循环
	                Cell first = row.createCell(0);
	                first.setCellStyle(style);
	                first.setCellValue(list.get(0));
	                
	                Cell second = row.createCell(1);
	                second.setCellStyle(style);
	                second.setCellValue(list.get(1));
	                
	                Cell three = row.createCell(2);
	                three.setCellStyle(style);
	                three.setCellValue(list.get(2));
	                
	                Cell four = row.createCell(3);
	                four.setCellStyle(style);
	                four.setCellValue(list.get(3));
	                
	                Cell five = row.createCell(4);
	                five.setCellStyle(style);
	                five.setCellValue(list.get(4));
	                
	                Cell six = row.createCell(5);
	                six.setCellStyle(style);
	                six.setCellValue(list.get(5));
	                
	                Cell seven = row.createCell(6);
	                seven.setCellStyle(style);
	                seven.setCellValue(list.get(6));
	                
	                Cell eight = row.createCell(7);
	                eight.setCellStyle(style);
	                eight.setCellValue(list.get(7));
	                
	                Cell nine = row.createCell(8);
	                nine.setCellStyle(style);
	                nine.setCellValue(list.get(8));
	                
	                Cell ten = row.createCell(9);
	                ten.setCellStyle(style);
	                ten.setCellValue(list.get(9));
	                
	                Cell enlven = row.createCell(10);
	                enlven.setCellStyle(style);
	                enlven.setCellValue(list.get(10));
	                
	                Cell twlev = row.createCell(11);
	                twlev.setCellStyle(style);
	                twlev.setCellValue(list.get(11));
	                
	                Cell third = row.createCell(12);
	                third.setCellStyle(style);
	                third.setCellValue(list.get(12));
	                
	                Cell fourteen = row.createCell(13);
	                fourteen.setCellStyle(style);
	                fourteen.setCellValue(list.get(13));
	                
	                Cell fifteen = row.createCell(14);
	                fifteen.setCellStyle(style);
	                fifteen.setCellValue(list.get(14));
	                
	                Cell sixteen = row.createCell(15);
	                sixteen.setCellStyle(style);
	                sixteen.setCellValue(list.get(15));
	                
	                Cell seventeen = row.createCell(16);
	                seventeen.setCellStyle(style);
	                seventeen.setCellValue(list.get(16));
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            workBook.write(os);
//
//            DownloadUtil downloadUtil = new DownloadUtil();             //直接弹出下载框，用户可以打开，可以保存
//            downloadUtil.download(out, response, fileName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 越南表格导出
     * @param dataList
     * @param finalXlsxPath
     */
    public static void writeExcel1(List<JSONObject> dataList, String finalXlsxPath){    	
    	OutputStream out = null;
        try {
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            HSSFWorkbook workBook = (HSSFWorkbook) getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            HSSFCellStyle style = workBook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
//            for (int i = 1; i <= rowNumber; i++) {
//                Row row = sheet.getRow(i);
//                sheet.removeRow(row);
//            }

            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
            	//创建容器
            	List<String> list = new LinkedList<String>();
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 2);
                // 得到要插入的每一条记录
                JSONObject jsonObject = dataList.get(j);
                //获取用户信息
                String guNo = jsonObject.getString("guNo");
                list.add(guNo);
                String guNameCn = jsonObject.getString("guNameCn");
                list.add(guNameCn);
                String guNameEn = jsonObject.getString("guNameEn");
                list.add(guNameEn);
                String guSexC = jsonObject.getString("guSexC");
                list.add(guSexC);
                String guBirthDate = jsonObject.getString("guBirthDate");
                list.add(guBirthDate);
                String guNationalityC = jsonObject.getString("guNationalityC");
                list.add(guNationalityC);
                String guPassportNum = jsonObject.getString("guPassportNum");
                list.add(guPassportNum);   
                String gvEntryDate = jsonObject.getString("gvEntryDate");
                list.add(gvEntryDate);
                String gvLeaveDate = jsonObject.getString("gvLeaveDate");
                list.add(gvLeaveDate);
                String guIssueDate = jsonObject.getString("guIssueDate");
                list.add(guIssueDate);
                String guExpiryDate = jsonObject.getString("guExpiryDate");
                list.add(guExpiryDate);      
                String gvFlightNum = jsonObject.getString("gvFlightNum");
                list.add(gvFlightNum);
                String gvPrintPlace = jsonObject.getString("gvPrintPlace");
                list.add(gvPrintPlace);
                String gvAirport = jsonObject.getString("gvAirport");
                list.add(gvAirport);
                
                //写入表格
                for (int k = 0; k < list.size(); k++) {
	                // 在一行内循环
	                Cell first = row.createCell(0);
	                first.setCellStyle(style);
	                first.setCellValue(list.get(0));
	                
	                Cell second = row.createCell(1);
	                second.setCellStyle(style);
	                second.setCellValue(list.get(1));
	                
	                Cell three = row.createCell(2);
	                three.setCellStyle(style);
	                three.setCellValue(list.get(2));
	                
	                Cell four = row.createCell(3);
	                four.setCellStyle(style);
	                four.setCellValue(list.get(3));
	                
	                Cell five = row.createCell(4);
	                five.setCellStyle(style);
	                five.setCellValue(list.get(4));
	                
	                Cell six = row.createCell(5);
	                six.setCellStyle(style);
	                six.setCellValue(list.get(5));
	                
	                Cell seven = row.createCell(6);
	                seven.setCellStyle(style);
	                seven.setCellValue(list.get(6));
	                
	                Cell eight = row.createCell(7);
	                eight.setCellStyle(style);
	                eight.setCellValue(list.get(7));
	                
	                Cell nine = row.createCell(8);
	                nine.setCellStyle(style);
	                nine.setCellValue(list.get(8));
	                
	                Cell ten = row.createCell(9);
	                ten.setCellStyle(style);
	                ten.setCellValue(list.get(9));
	                
	                Cell enlven = row.createCell(10);
	                enlven.setCellStyle(style);
	                enlven.setCellValue(list.get(10));
	                
	                Cell twlev = row.createCell(11);
	                twlev.setCellStyle(style);
	                twlev.setCellValue(list.get(11));
	                
	                Cell third = row.createCell(12);
	                third.setCellStyle(style);
	                third.setCellValue(list.get(12));
	                
	                Cell fourteen = row.createCell(13);
	                fourteen.setCellStyle(style);
	                fourteen.setCellValue(list.get(13));
	                
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            workBook.write(os);
//
//            DownloadUtil downloadUtil = new DownloadUtil();             //直接弹出下载框，用户可以打开，可以保存
//            downloadUtil.download(out, response, fileName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }
    
    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
