package com.build123.lifemall.common.encryption;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 14-3-28.
 */
public class MD5 {
    public static String crypt(String str) throws NoSuchAlgorithmException {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }

        return hexString.toString();
    }

    /**
     * 登录用户名密码加密
     * @param password 密码
     * @param loginname 用户名
     * @param algorithm  加密规则
     * @return
     * @throws Exception
     */
    public static String getHmacMD5(String password,String loginname, String algorithm){
        byte[] keyBytes = password.getBytes();
        Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
        Mac mac=null;
        try {
            mac = Mac.getInstance(algorithm);
            mac.init(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return byteArrayToHex(mac.doFinal(loginname.getBytes()));
    }

    /**
     * 16进制加密
     * @param a
     * @return
     */
    protected static String byteArrayToHex(byte [] a) {
        int hn, ln, cx;
        String hexDigitChars = "0123456789abcdef";
        StringBuffer buf = new StringBuffer(a.length * 2);
        for(cx = 0; cx < a.length; cx++) {
            hn = ((int)(a[cx]) & 0x00ff) /16 ;
            ln = ((int)(a[cx]) & 0x000f);
            buf.append(hexDigitChars.charAt(hn));
            buf.append(hexDigitChars.charAt(ln));
        }
        return buf.toString();

    }
}
