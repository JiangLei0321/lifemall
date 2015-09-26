package com.build123.lifemall.common.util;

import java.util.UUID;

/**
 * Created by jiang.liu on 14-8-12.
 */
public class OrderUtil {
    /**
     * 生成订单号
     *
     * @return
     */
    public static String getOrderCodeNO() {
        StringBuffer orderStringBuffer = new StringBuffer();
        /** 追加时间字符串 **/
        orderStringBuffer.append(DateUtil.getCurrentDateString());
        /** 实例化UUID **/
        UUID random = UUID.randomUUID();
        String[] randoms = random.toString().split("-");
        /** 追加uuid随机串 **/
        orderStringBuffer.append(randoms[0]);
        orderStringBuffer.append(randoms[1]);
        /** 返回生成好的商城订单号 **/
        return orderStringBuffer.toString();
    }

    /**
     * 获取随机字符串
     */
    public static String getUUID() {
        UUID random = UUID.randomUUID();
        return (random.toString().split("-")[0]);
    }
}
