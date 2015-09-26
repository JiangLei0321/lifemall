package com.build123.lifemall.common.util;

import com.build123.lifemall.common.model.SharePageEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
   
/**
 * Created by YS on 14-3-28.
 */
public class BaseController {

    /**
     * 添加更新数据时，判断是否成功
     *
     * @param resultId         当前操作的主键ID
     */
    public static JSONObject isScueessMessage(Integer resultId) {
        JSONObject resultJsonObject = new JSONObject();
        if (resultId != null && resultId > 0) {
            //新生成的Id
            resultJsonObject.put("id", resultId);
            //执行方法成功
            resultJsonObject.put("success", true);
            //错误代码
            resultJsonObject.put("errorcode", "");
            //错误信息
            resultJsonObject.put("errortext", "");
        } else {
            //新生成的Id
            resultJsonObject.put("id", 0);
            //执行方法失败
            resultJsonObject.put("success", false);
            //错误代码
            resultJsonObject.put("errorcode", "100");
            //错误信息
            resultJsonObject.put("errortext", "参数对象为空，或数据库sql语句执行失败！");
        }
        return resultJsonObject;
    }

    /*  获得URI某段 */
    public static String getUriNode(String startItem, int i, HttpServletRequest request) {
        String node = null;
        String template = "/" + startItem + "/";
        String uri = request.getRequestURI();
        int startPos=uri.indexOf(template);
        if(startPos>=0){
            uri=uri.substring(startPos+template.length());
            if(uri.isEmpty()){
                String[] splitList=uri.split("/");
                if(i>0&&i<uri.length()){
                    node=splitList[i];
                }
            }

        }
        return node;
    }

    /**
     * 获取post类型参数
     * @return
     */
    public static String getPostParam(HttpServletRequest request){
        BufferedReader br=null;
        StringBuilder sb =null;
        String value = "";
        try {
            br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf-8"));
            String line = null;
            sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            System.out.println("POST PUT参数:"+sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(null != sb) {
            value = sb.toString();
        }
        return value;
    }

    /**
     * 取得参数
     * @param request
     * @param key  参数名
     * @param defaultValue  默认值
     * @return
     */
    public static String getParameter(HttpServletRequest request,String key, String defaultValue) {
        String ret = "";
        try {
            if ("flag".equals(key)) {
                return defaultValue;
            }
            if (request.getParameter(key) == null
                    || request.getParameter(key).isEmpty()) {
                return defaultValue;
            }
            ret = URLDecoder.decode(request.getParameter(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 删除输出信息
     * @param resultRowCount
     * @return
     */
    public static JSONObject deleteMessage(Integer resultRowCount){
        JSONObject resultJsonObject=new JSONObject();
        if(resultRowCount>0){
            //执行方法成功
            resultJsonObject.put("success", true);
            //错误代码
            resultJsonObject.put("errorcode", "");
            //错误信息
            resultJsonObject.put("errortext", "");
        }else{
            //执行方法失败
            resultJsonObject.put("success", false);
            //错误代码
            resultJsonObject.put("errorcode", "0001");
            //错误信息
            resultJsonObject.put("errortext", "系统繁忙，请刷新重试。");
        }
        return resultJsonObject;
    }

    /**
     * 输出对象为空信息
     * @param resultJsonObject
     */
    public static void objectEmptyMessage(JSONObject resultJsonObject){
        /** 如果传入的JSON为空 **/
        if(resultJsonObject==null) {
            /** 实例化JSON对象 **/
            resultJsonObject=new JSONObject();
        }
        resultJsonObject.put("id", 0);
        resultJsonObject.put("success", false);
        resultJsonObject.put("errorcode", "");
        resultJsonObject.put("errortext", "参数对象为空！");
    }

    /**
     * 输出执行错误信息
     * @param root
     * @param e
     */
    public static void errorMessage(JSONObject root,Exception e){
        if(root==null) root=new JSONObject();
        root.put("id", 0);
        root.put("success", false);
        //错误代码
        root.put("errorcode", e.hashCode());
        //错误信息
        root.put("errortext", e.getMessage());
    }

    /**
     * 设置分页参数
     */
    public static JSONObject setSharePageParam(HttpServletRequest request){
        JSONObject sharePageParamJSON=new JSONObject();
        try {
            //设置排序参数
            String sortStr=request.getQueryString();
            if(sortStr!=null && !sortStr.equals("")){
                /**设置分页参数**/
                sharePageParamJSON=setQueryParams(request);
                if(sortStr.indexOf("sort(")>0){
                    /**设置排序参数**/
                    String sortTemp=sortStr.substring(sortStr.indexOf("(")+1, sortStr.indexOf(")"));
                    //获取排序（+升序 -降序）
                    String sort=sortTemp.substring(0,1);
                    String orderSort=sort.trim().equals("+")?"ASC":"DESC";
                    //设置排序（+升序 -降序）
                    sharePageParamJSON.put("orderSort", orderSort);
                    String orderColumn=sortTemp.substring(1,sortTemp.length());
                    //设置排序字段
                    sharePageParamJSON.put("orderColumn", orderColumn);
                }
            }
            //读取HTTP HEADER信息
            String range=request.getHeader("Range");
            if(range!=null && !range.equals("")){
                String[] items=range.split("=");
                //获取分页参数，设置分页起始位置和分页大小
                String[] shareParams=items[1].split("-");
                int index=Integer.parseInt(shareParams[0]);
                //设置分页起始位置
                sharePageParamJSON.put("index", index);
                int end=Integer.parseInt(shareParams[1]);
                //分页结束位置
                sharePageParamJSON.put("end", end);
                ScriptEngineManager sem = new ScriptEngineManager();
                ScriptEngine se = sem.getEngineByName("javascript");
                int row=(int) Math.abs((Double)se.eval(items[1]))+1;
                //设置分页当前查询条数
                sharePageParamJSON.put("row", row);
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return sharePageParamJSON;
    }

    /**
     * 设置查询参数
     * @param
     */
    public static JSONObject setQueryParams(HttpServletRequest request) {
        JSONObject queryParamJSON=new JSONObject();
        Enumeration<?> paramEnum = request.getParameterNames();
        while (paramEnum.hasMoreElements()) {
            String param = paramEnum.nextElement().toString();
            if (!param.startsWith("sort(")) {
                String[] values =  request.getParameterValues(param);
                if (values.length > 0 && (values.length > 1 || !values[0].isEmpty())) {
                    queryParamJSON.put(param, values[0]);
                }
            }
        }
        return queryParamJSON;
    }

    /**
     * 分页响应内容
     * @param root 显示分页的集合
     * @param pageEntity 需要分页的Java对象
     *
     */
    public static void getSharePageContext(HttpServletResponse response,JSONObject root,SharePageEntity pageEntity){
        try {
            //获取分页总数量
            int totalCount=Integer.parseInt(root.getString(SharePageEntity.SHAREPAGE_COUNT_STR));
            if(totalCount!=SharePageEntity.NOTSHARE_TOTALCOUNT){
                //输出格式items 0-9/10
                int endIndex=(totalCount>pageEntity.getRow())?pageEntity.getEnd():totalCount-1;
                if(root.get(SharePageEntity.SHAREPAGE_ITEMS_STR) == null) {
                    totalCount = 0;
                    endIndex=0;
                }
                String ContentRange="items "+pageEntity.getIndex()+"-"+endIndex+"/"+totalCount;
                response.setHeader("Content-Range", ContentRange);
            }
            responseJson(root.get(SharePageEntity.SHAREPAGE_ITEMS_STR),response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询单条数据输出方法
     * @param root 返回的数据
     *
     */
    public static void getJSONContextById(HttpServletResponse response,JSONObject root){
        try {
            root=((JSONArray) root.get(SharePageEntity.SHAREPAGE_ITEMS_STR)).getJSONObject(0);
            responseJson(root,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出response信息（json格式）
     * @param obj
     * @param response
     * @throws Exception
     */
    public static void responseJson(Object obj,HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if(obj==null){
                pw.write(new JSONObject().toString());
            }else{
                pw.write(obj.toString());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != pw){
                pw.flush();
                pw.close();
            }
        }
    }
}
