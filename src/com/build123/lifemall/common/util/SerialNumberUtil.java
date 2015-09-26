package com.build123.lifemall.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiang.liu on 14-5-8.
 */
public class SerialNumberUtil {
    /** 流水号 **/
    private static String serialNumber = "";

    /**
     * 生产流水号
     *
     * @return
     */
    public static String productSerialNumber(){
        serialNumber = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) +
                        new DecimalFormat("000").format(1000*Math.random());
        return serialNumber;
    }
}
