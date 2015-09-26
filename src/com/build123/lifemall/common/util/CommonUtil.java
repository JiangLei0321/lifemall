package com.build123.lifemall.common.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

public class CommonUtil {
	
	/**
	 * 获取完整url连接
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		if (request == null)return "";
		String url = "";
		url = request.getContextPath();
		url = url + request.getServletPath();

		Enumeration<?> names = request.getParameterNames();
		int i = 0;
		if (names != null) {
			while (names.hasMoreElements()) {
					String name = (String) names.nextElement();
					if (i == 0) {
						url = url + "?";
					} else {
						url = url + "&";
					}
					i++;
					String value = request.getParameter(name);
					if (value == null) {
						value = "";
					}
					url = url + name + "=" + value;
			}
		}
		return url;
		}  
	
	/**
	 * 计算两个日期之间相差的天数
	 * @author ydd
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getQuot(String time1, String time2){
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date() ;Date date2=new Date();
		try {
			date1 = ft.parse( time1 );
			date2 = ft.parse( time2 );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}
	public static long getQuot1(String time1, String time2){
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1=new Date() ;Date date2=new Date();
		try {
			date1 = ft.parse( time1 );
			date2 = ft.parse( time2 );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}
	/**
	 * 获取当前时间(yyyy-MM-dd)
	 * @author ydd
	 * @return
	 */
	public static String getDateDay(){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		return ft.format(new Date());
	}
	/**
	 * 获取当前时间(yyyyMMdd)
	 * @author ydd
	 * @return
	 */
	public static String getDateDayFormat2(){
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		return ft.format(new Date());
	}
	/**
	 * 获取当前时间(yyyy-MM-dd HH:mm:ss)
	 * @author ydd
	 * @return
	 */
	public static String getDateDayTime(){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ft.format(new Date());
	}
	/**
	 * 格式化日期字符串
	 * @param dataStr 日期字符串
	 * @return (yyyy-MM-dd)
	 */
	public static String getDateStringFomart(String dataStr){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		return ft.format(getDateByStringDate(dataStr));
	}
	/**
	 * 格式化日期字符串
	 * @param dataStr 日期字符串
	 * @return (yyyy-MM-dd)
	 */
	public static Date getDateFomartToString(String dataStr){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return ft.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 格式化日期字符串
	 * @param date 时间
	 * @return (yyyy-MM-dd HH:mm:ss)
	 */
	public static String getDateTimeStringFomart(Date date){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ft.format(date);
	}
	/**
	 * 日期字符串装换为Date类型
	 * @param dataStr 日期字符串(格式：yyyyMMdd)
	 * @return Date
	 */
	public static Date getDateByStringDate(String dataStr){
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		try {
			return ft.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 计算年龄
	 * @param birthDay
	 * @return
	 */
	public static String getAge(Date birthDay) {
	        Calendar cal = Calendar.getInstance();
	        int yearNow = cal.get(Calendar.YEAR);
	        int monthNow = cal.get(Calendar.MONTH)+1;
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
	       
	        cal.setTime(birthDay);
	        int yearBirth = cal.get(Calendar.YEAR);
	        int monthBirth = cal.get(Calendar.MONTH);
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

	        int age = yearNow - yearBirth;

	        if (monthNow <= monthBirth) {
	            if (monthNow == monthBirth) {
	                //monthNow==monthBirth
	                if (dayOfMonthNow < dayOfMonthBirth) {
	                    age--;
	                }
	            } else {
	                //monthNow>monthBirth
	                age--;
	            }
	        }
	        return age +"";
	    }
	
	
	/**
	 * 获取随机字符串
	 */
	public static String getUUID() {
		UUID random=UUID.randomUUID();
        String[] subUUID = random.toString().split("-");
        String resultUUID = subUUID[0] + subUUID[1] + subUUID[2];
		return resultUUID;
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public static String getOrderCodeNo(){
		StringBuffer orderCodeNo=new StringBuffer();
		//追加时间字符串
		orderCodeNo.append(getDateDayFormat2());
		//获取uuid
		UUID random=UUID.randomUUID();
		String[] randoms=random.toString().split("-");
		//追加uuid随机串
		orderCodeNo.append(randoms[0]);
		orderCodeNo.append(randoms[1]);
		return orderCodeNo.toString();
	}
	
	
	public static void responseFile(String filePath, HttpServletResponse resp) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		File file = new File(filePath);
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			out = new BufferedOutputStream(resp.getOutputStream());
			out.write(buffer);
		}finally {
			if (null != in) {
				try {
					if (in != null)
						in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file.delete();
		}
	}
	
}
