package com.build123.lifemall.common.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by changqing.cui on 14-5-16.
 */
public class XmlUtil {

    /** 日志记录器 **/
    private static final Logger log = Logger.getLogger(XmlUtil.class);

    /**
     * XML文档解析工具方法
     *
     * @param xml
     * @param root
     * @return
     */
    public static Map<String, String> xml(String xml, String root) {
        /** 如果xml为空，则返回空 **/
        if (xml == null) {
            return null;
        }

        /** 实例化语法解析器 **/
        SAXReader reader = new SAXReader(false);
        Document document = null;
        try {
            document = reader.read(new StringReader(xml));
        } catch (DocumentException e){
            log.error("读取Xml文档发生错误", e);
            return null;
        }

        /** 获取文档的根节点 **/
        Element rootNode = document.getRootElement();
        if (rootNode == null || !rootNode.getName().equalsIgnoreCase(root)) {
            return null;
        }
        /** 新建一个map集合，保存将要返回的结果集 **/
        Map<String, String> map = new HashMap<String, String>();
        for (Object obj : rootNode.elements()) {
            Element element = (Element) obj;
            if (!element.isTextOnly()) {
                map.put(element.getName().toLowerCase(), element.asXML());
            }
            else {
                map.put(element.getName().toLowerCase(), element.getText());
            }
        }
        return map;
    }
}
