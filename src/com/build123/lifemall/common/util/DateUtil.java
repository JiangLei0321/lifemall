package com.build123.lifemall.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiang.liu on 14-8-12.
 */
public class DateUtil {

    /**
     * 获取时间戳
     *
     * @return
     * @throws java.text.ParseException
     */
    public long getTimestamp() throws ParseException {

        Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2009/12/11 00:00:00");
        Date date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("1970/01/01 08:00:00");
        long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime() - date2.getTime() : date2.getTime() - date1.getTime();
        long credate = (int) (Math.random() * 1000);
        return credate;
    }


    /**
     * 获取当前时间(yyyyMMdd)
     *
     * @return
     */
    public static String getCurrentDateString() {
        /** 时间格式化对象 **/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }
}
