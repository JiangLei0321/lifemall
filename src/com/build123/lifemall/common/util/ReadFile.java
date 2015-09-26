package com.build123.lifemall.common.util;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * io流读取文件类容
 * @author Administrator
 *
 */
public class ReadFile {
	protected static Logger log = Logger.getLogger(ReadFile.class.getName());
	/**
	 * 根据文件路径顺序读取文件类容
	 * @author 殷冬冬
	 * @date 2011-10-19 10:13
	 * @param path 文件路径
	 * @return 文件类容
	 */
	@SuppressWarnings("resource")
	public static String readFileToString(String path){
		StringBuffer sBuffer=new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(path), "UTF-8"));
		int b;
		while( (b=br.read())!=-1 ) //顺序读取文件text里的内容并赋值
		{ 
		  //给整型变量b,直到文件结束为止。
		  sBuffer.append((char)b);
		}
		}catch(FileNotFoundException e ){
		  e.printStackTrace();
		}catch(IOException e1 ){
		  e1.printStackTrace();
		}
		return sBuffer.toString();
	}
	/**
	 * 通过java IO 得到文件字符串,在进行转换为JSon对象
	 * @param ioString
	 * @author 殷冬冬
	 * @return
	 */
	public static JSONObject FileTtJson(String jsonString){
		 //通过java IO 得到文件字符串 
		 //JSONObject  jsonObj  = JSONObject.fromObject(jsonString);
		 //JSONObject AObj = jsonObj.getJSONObject("history");
		 //A a = JSONObject.toBean(AObj, A.class); //得到A对象
		 //Object[] listB = (jsonObj.getJSONArray("origin").toArray()); //得到B的List集合
		 //List listC = Array.asList(jsonObj.getJSONArray("C").toArray()); //得到C的List集合
		 //jsonObj.getJSONArray("C").toArray();
		 //for(int i=0;i<listB.length;i++){
			// System.out.println(listB[i].toString());
		 //}
		 return null;
	}
	
	
	/**
	 * @function 根据指定的路径path读取json文件内所有的数据。
	 * @author 殷冬冬
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {
		File fileMk = new File(path.substring(0, path.lastIndexOf(File.separator)));
		if (!fileMk.exists()) {
			fileMk.mkdirs();
		}
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		BufferedReader br = null;
		String laststr = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));  

			String tempString = null;
				// 一次读入一行，直到读入null为文件结束
			while ((tempString = br.readLine()) != null) {
				// 显示行号
				laststr = laststr + tempString;
			    //laststr = new String(laststr.getBytes("ISO8859-1"),"UTF-8");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return laststr;
	}
	/**
	 * @author 殷冬冬
	 * @function 读取json数据，返回list
	 * @param path
	 * @param clas 对应JSON数据的实体类
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access", "rawtypes"})
	public static List getJsonToList(String path, Class clas) {
		ArrayList list = new ArrayList();
		String data = readFile(path);
		if(null == data || "".equals(data)){
			return list;
		}
		JSONArray arry = JSONArray.fromObject(data);
		JSONObject jsonObj = null;
		Object obj = null;
		for (int i = 0; i < arry.size(); i++) {
			jsonObj = arry.getJSONObject(i);
			obj = jsonObj.toBean(jsonObj, clas);
			list.add(obj);
		}
		return list;
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
			FileOutputStream writerStream = new FileOutputStream(path);
			utput = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			utput.write(context);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			utput.close();
		}
		return false;
	}
}
