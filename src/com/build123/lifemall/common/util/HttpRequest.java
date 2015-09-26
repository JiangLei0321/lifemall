package com.build123.lifemall.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 通过get、post方式对http url进行传参请求
 * 返回请求结果
 * Created by Administrator on 03.02.15.
 */
public class HttpRequest {

    private static final Log logger = LogFactory.getLog(HttpRequest.class);
    /**
     * 发送http get请求
     * @param configUtil
     * @param url_address
     * @param param
     * @return
     */
    public static JSONObject sendGet(ConfigUtil configUtil, String url_address, String param, Object dynamic) {
        logger.info("httpUrlConnection->sendGet->执行sendGet方法");
        logger.info("httpUrlConnection->sendGet->url_address:"+url_address);
        logger.info("httpUrlConnection->sendGet->param:"+param);

        JSONObject jsonObject = null;
        try {
            URL url = new URL(url_address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.360");
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("X-AVOSCloud-Application-Id", configUtil.getAppId());
            connection.setRequestProperty("X-AVOSCloud-Application-Key", configUtil.getAppKey());
            connection.setRequestProperty("X-AVOSCloud-Master-Key", configUtil.getMasterKey());
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json;application/x-www-form-urlencoded");
            connection.connect();
            //接收返回请求
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line="";
            StringBuffer buffer=new StringBuffer();
            while((line=reader.readLine())!=null){
                buffer.append(line);
            }
            String result=buffer.toString();
            if(url_address.indexOf("resulttype=array") >= 0) {
                JSONArray array = JSONArray.fromObject(result);
                jsonObject = new JSONObject();
                jsonObject.put("data", array);
            } else {
                jsonObject = JSONObject.fromObject(result);
            }
            logger.info("httpUrlConnection->sendGet->执行sendGet方法");
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 发送http post请求
     * @param configUtil
     * @param url_address
     * @param param
     * @return
     */
    public static JSONObject sendPost(ConfigUtil configUtil, String url_address, String param, Object dynamic) {

        JSONObject jsonObjectResult = null;
        try {
            URL url = new URL(url_address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if(dynamic != null) {
                if(dynamic == "upload") {
                    connection.setRequestMethod("POST");
                } else {
                    connection.setRequestMethod("PUT");
                }
            } else {
                connection.setRequestMethod("POST");
            }
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("X-AVOSCloud-Application-Id", configUtil.getAppId());
            connection.setRequestProperty("X-AVOSCloud-Application-Key", configUtil.getAppKey());
            connection.setRequestProperty("X-AVOSCloud-Master-Key", configUtil.getMasterKey());
            byte[] dataIconparam = null;
            if(dynamic != null) {
                if(dynamic == "upload") {
                    BASE64Decoder decoder = new BASE64Decoder();
                    connection.setRequestProperty("Content-Type", "image/png");
                    connection.setRequestProperty("Accept", "*/*"); // 设置接收数据的格式
                    dataIconparam =  decoder.decodeBuffer(param);
                } else {
                    connection.setRequestProperty("X-AVOSCloud-Session-Token", (String) dynamic);
                    connection.setRequestProperty("Content-Type","application/json;application/x-www-form-urlencoded;charset=utf-8");
                }
            } else {
                connection.setRequestProperty("Content-Type","application/json;application/x-www-form-urlencoded");
            }
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());

            System.out.println(param);
            JSONObject jsonObject = null;
            if(dynamic != null) {
                if(dynamic == "upload") {
                    out.write(dataIconparam);
                } else {
                    jsonObject = JSONObject.fromObject(param);
                    out.writeBytes(jsonObject.toString());
                }
            } else {
                jsonObject = JSONObject.fromObject(param);
                out.writeBytes(jsonObject.toString());
            }
            out.flush();
            out.close(); //
            //接收返回请求
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line="";
            StringBuffer buffer=new StringBuffer();
            while((line=reader.readLine())!=null){
                buffer.append(line);
            }
            String result=buffer.toString();
            jsonObjectResult = JSONObject.fromObject(result);
            logger.info("httpUrlConnection->sendPost->执行sendPost方法返回的参数:");
            System.out.println(result);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObjectResult;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws
     */
    public final static String getIpAddress(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}