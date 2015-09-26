/*
 * 版权所有 (C) 2001-2013 深圳市艾派应用系统有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、Oct 16, 2013，ccq创建。 
 */
package com.build123.lifemall.common.util;


import org.apache.commons.httpclient.methods.PostMethod;

public class UTF8PostMethod extends PostMethod
{
    public UTF8PostMethod(String url)
    {
        super(url);
    }

    @Override
    public String getRequestCharSet()
    {
        return "UTF-8";
    }
}
