package com.build123.lifemall.common.util;


/**
 * 作者：ydd
 * 时间：2011-03-28
 * 功能：json格式和java对象的转换
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonUtil {

	/**
	 * 将json字符串格式转换为对象
	 * @param jsonstr
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	public static Object getJsonToObject(String jsonstr,Class pojo){
		Object obj;
		JSONObject jsonObject= JSONObject.fromObject(jsonstr);
		obj=jsonObject.toBean(jsonObject, pojo);
		return obj;
	}
	
	/**
	 * 将json字符串转换为map
	 * @param jsonstr
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getJsonToMap(String jsonstr){
		JSONObject jsonObject= JSONObject.fromObject(jsonstr);
		Iterator  it=jsonObject.keys();
		String keys=null;
		Object obj=null;
		Map map=new HashMap();
		while(it.hasNext()){
			keys=(String)it.next();
			obj=jsonObject.get(keys);
			map.put(keys, obj);
			System.out.println(map.get(keys));
		}
		return map;
	}
	/**
	 * 将json的字符串转换为list对象
	 */
	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	public static List getJsonToList(String jsonstr,Class clas){
		JSONArray arry= JSONArray.fromObject(jsonstr);
		JSONObject jsonObj=null;
		Object obj=null;
		ArrayList list=new ArrayList();
		for (int i = 0; i < arry.size(); i++) {
			jsonObj=arry.getJSONObject(i);
			obj=jsonObj.toBean(jsonObj, clas);
			list.add(obj);
		}
		return list;
	}
	/**
	 *将java对象转换成json
	 */
	public static String getObjectToJsonString(Object object){
		String str=null;
		JSONObject jsonObject= JSONObject.fromObject(object);
		str=jsonObject.toString();
		return str;
		
	}
	/**
	 * 写json文件
	 * @author 殷冬冬
	 * @param context 要输入json文件的类容
	 * @param path    json文件路径
	 * @return 是否执行成功
	 * @date 2011-10-29 15:59:17
	 * @throws java.io.IOException
	 */
	public static boolean jsonFileWrite(String context, String path)throws IOException {
		BufferedWriter utput = null;
		try {
			java.io.FileOutputStream writerStream = new java.io.FileOutputStream(path); 
			utput = new BufferedWriter(new java.io.OutputStreamWriter(writerStream, "UTF-8"));  
			utput.write(context);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			utput.close();
		}
		return false;
	}

	/**
	 * 写入数据
	 * @author 殷冬冬
	 * @date 2011-10-17 15:21
	 */
	public static void WriteData(String path) {
		try {
			File file = new File(path);
			file.mkdirs();
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
