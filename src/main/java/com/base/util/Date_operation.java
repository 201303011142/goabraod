package com.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Date_operation {
	/**
	 * method 计算两个日期之间的天数
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int differentDays(String str1,String str2){
		
		 Date date1 = null;
		 Date date2 = null;
		try {
			 date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE").parse(str1);
			 date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE").parse(str2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	       int day1= cal1.get(Calendar.DAY_OF_YEAR);
	        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
	        
	        int year1 = cal1.get(Calendar.YEAR);
	        int year2 = cal2.get(Calendar.YEAR);
	        if(year1 != year2){
	            int timeDistance = 0 ;
	            for(int i = year1 ; i < year2 ; i ++)
	            {
	                if(i%4==0 && i%100!=0 || i%400==0){
	                    timeDistance += 366;
	                }
	                else{
	                    timeDistance += 365;
	                }
	            }
	            
	            return timeDistance + (day2-day1) ;
	        }
	        else{
	          
	            return day2-day1;
	        }
	    }
	
	/**
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int differentDaysl(String str1,String str2){
		
		 Date date1 = null;
		 Date date2 = null;
		try {
			 date1 = new SimpleDateFormat("yyyy-MM-dd").parse(str1);
			 date2 = new SimpleDateFormat("yyyy-MM-dd").parse(str2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	       int day1= cal1.get(Calendar.DAY_OF_YEAR);
	        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
	        
	        int year1 = cal1.get(Calendar.YEAR);
	        int year2 = cal2.get(Calendar.YEAR);
	        if(year1 != year2){
	            int timeDistance = 0 ;
	            for(int i = year1 ; i < year2 ; i ++)
	            {
	                if(i%4==0 && i%100!=0 || i%400==0){
	                    timeDistance += 366;
	                }
	                else{
	                    timeDistance += 365;
	                }
	            }
	            
	            return timeDistance + (day2-day1) ;
	        }
	        else{
	          
	            return day2-day1;
	        }
	    }
	
	public static String startStr(String str) {
        String[] strs = str.split("--");
        String total = strs[strs.length - 1];
        String startHour = total.substring(0, total.indexOf(":"));
        if ((total.charAt(total.indexOf("M") - 1) + "").equals("A")
                && (startHour.equals("12"))) {
            startHour = Integer.valueOf(startHour) - 12 + "";
        }
        if ((total.charAt(total.indexOf("M") - 1) + "").equals("P")
                && (!startHour.equals("12"))) {
            startHour = Integer.valueOf(startHour) + 12 + "";
        }
        if ((total.charAt(total.indexOf("M") - 1) + "").equals("P")
                && (startHour.equals("12"))) {
            startHour = Integer.valueOf(startHour) + "";
        }
        startHour += total
                .substring(total.indexOf(":"), total.indexOf("M") - 1);
        return startHour;
    }
	
    public static String getAnyData(int distance) {
        
    	String format2 = "";
    	try {
           Date parse = new SimpleDateFormat("yyyyMMdd").parse("19000101");
          Calendar instance = Calendar.getInstance();
          instance.setTime(parse);
          instance.add(Calendar.DATE, distance-2);
          format2 = new SimpleDateFormat("yyyyMMdd").format(instance.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  format2 ;
    }
	
	/**
	 * 是否年满16岁
	 * @param birthday
	 * @return
	 */
	@SuppressWarnings("deprecation")
   public static boolean moreSixteen(String birthday) {
	   //全局变量
	   boolean moreSixteen = true;
	   try {
		   //出生日期
			Date parse = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			//今天日期
			Date parse2 = new Date();
			//获取相差的年数
			int year = parse2.getYear() - parse.getYear();
			//获取相差的月份
			int month = parse2.getMonth() - parse.getMonth();
			//获取相差的天数
			int day = parse2.getDate() - parse.getDate();
			//判断
			if (year>=16) {
				if (month>0) {
					moreSixteen = true;
				}else if (month==0) {
					if (day>0) {
						moreSixteen = true;
					}else {
						moreSixteen = false;
					}
				}else{
					moreSixteen = false;
				}
			}else {
				moreSixteen = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moreSixteen;
   }
}
