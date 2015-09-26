package com.build123.lifemall.common.util;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * 创建、解析token工厂工具，
 * Created by lei.jiang on 06.02.15.
 */
public class TokenUtil {
    private static final Log logger = LogFactory.getLog(TokenUtil.class);

    /**
     * 创建token
     * @param objectId    用户ID
     * @param ip          用户主机IP
     * @param token_key   加密关键字
     * @return            token字符串
     */
    public static JSONObject createToken(String objectId, String ip, String token_key) {
        JSONObject tokenJson = new JSONObject();
        try{
            logger.info("【TokenUtil createToken】进入token工厂开始生成token字符串");
            //对明文关键信息进行DES加密
            String objectId_psd = DESEncryption.encrypt(objectId, token_key);
            String ip_psd = DESEncryption.encrypt(ip, token_key);
            logger.info("【TokenUtil createToken】完成加密关键信息");
            //获取当前时间戳
            String timestamp = new Date().getTime()+"";
            logger.info("【TokenUtil createToken】生成时间戳");
            //拼接加密后的关键信息为一个字符串，以crypt_key为分隔符
            String tokenStr = objectId_psd+token_key+ip_psd+token_key+timestamp;
            //将拼接好的关键信息字符串，与ken进行DES加密
            String token = DESEncryption.encrypt(tokenStr, token_key);
            logger.info("【TokenUtil createToken】在token工厂中生成字符串为：");
            logger.info("【TokenUtil createToken】"+token);
            //加密时间戳
            String timestamp_psd = DESEncryption.encrypt(timestamp, token_key);
            //保存token到jsonObject中
            tokenJson.put("token", token);
            //保存加密时间戳到jsonObject中
            tokenJson.put("timestamp", timestamp_psd);
            //添加解析成功标识
            tokenJson.put("flag", true);
            logger.info("【TokenUtil createToken】token生成成功");
        } catch (Exception e) {
            //添加创建失败标识
            tokenJson.put("token", null);
            tokenJson.put("timestamp", null);
            tokenJson.put("flag", false);
            logger.info("【TokenUtil createToken】token生成失败");
        }
        return tokenJson;
    }

    /**
     * 创建token
     * @param token       token字符串
     * @param token_key   解密关键字
     * @return
     */
    public static JSONObject analysisToken(String token, String token_key) {
        JSONObject tokenJson = new JSONObject();
        try{
            logger.info("【TokenUtil analysisToken】进入token工厂开始解析token字符串");
            //将token字符串，与ken进行DES解密
            String tokenStr = DESEncryption.decrypt(token, token_key);
            logger.info("【TokenUtil analysisToken】解密token字符串完成");
            //解密后的字符串用crypt_key进行分割
            String[] keyStr = tokenStr.split(token_key);
            logger.info("【TokenUtil analysisToken】分割token字符串中关键信息");
            //获取分割后的字符串中关键信息
            String objectId = DESEncryption.decrypt(keyStr[0], token_key);
            String ip = DESEncryption.decrypt(keyStr[1], token_key);
            String timestamp = keyStr[2];
            logger.info("【TokenUtil analysisToken】解密关键信息字符串");
            //将关键信息封装到JSONObject对象中
            tokenJson.put("objectId", objectId);
            tokenJson.put("ip", ip);
            tokenJson.put("timestamp", timestamp);
            logger.info("【TokenUtil analysisToken】封装关键信息为JSONObject");
            //添加解析成功标识
            tokenJson.put("flag", true);
            logger.info("【TokenUtil analysisToken】解析成功");
        } catch (Exception e) {
            //添加解析失败标识
            tokenJson.put("flag", false);
            logger.info("【TokenUtil analysisToken】解析失败");
        }
        return tokenJson;
    }
}