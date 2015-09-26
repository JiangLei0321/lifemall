/*
 * 版权所有 (C) 2001-2013 深圳市艾派应用系统有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、Oct 16, 2013，ccq创建。 
 */
package com.build123.lifemall.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.pool.BasePoolableObjectFactory;

public class HttpClientPoolFactory extends BasePoolableObjectFactory<HttpClient>
{
    /**
     * 连接请求超时时间
     */
    private static final long REQ_TIMEOUT = 50 * 1000L;
    /**
     * 连接响应超时时间
     */
    private static final int RESP_TIMEOUT = 50 * 1000;

    @Override
    public HttpClient makeObject() throws Exception
    {
        HttpClient client = new HttpClient();
        HttpClientParams params = createHttpClientParams(REQ_TIMEOUT, RESP_TIMEOUT);
        client.setParams(params);
        return client;
    }

    /**
     * 构造http请求超时时间参数对象
     * @param reqTimeOut 单位毫秒
     * @param respTimeOut 单位毫秒
     * @return
     */
    private HttpClientParams createHttpClientParams(long reqTimeOut, int respTimeOut)
    {
        HttpClientParams params = new HttpClientParams();
        params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
        // 设置连接超时 单位毫秒
        params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, reqTimeOut);
        // 设置响应超时 单位毫秒
        params.setIntParameter(HttpClientParams.SO_TIMEOUT, respTimeOut);
        return params;
    }
}
