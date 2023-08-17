package com.stupidzhe.api.util;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.*;

/**
 * Created by Mr.W on 2017/5/15.
 * about http connecting
 */
public class HttpUtil {

    private static final String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    private static final String acceptEncoding = "gzip, deflate, br";
    private static final String acceptLanguage = "q=0.8,en-US;q=0.5,en;q=0.3";
    private static final String cacheControl = "max-age=0";
    private static final String connection = "keep-alive";
    private static final String host = "steamcommunity.com";
    private static final String upgradeInsecureRequests = "1";
    private static final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/603.3.4 (KHTML, like Gecko) Version/10.1.2 Safari/603.3.4";

    /**
     * GET method
     */
    public static final String METHOD_GET = "GET";

    /**
     * POST method
     */
    public static final String METHOD_POST = "POST";

    /**
     * input流转string
     */
    public static String getContent(InputStream inputStream) {
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String buff;
        StringBuilder content = new StringBuilder();
        try {
            while (null != (buff = bufferedReader.readLine())) {
                content.append(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }

    public static void addHeader(HttpRequestBase method, String cookies, String referer) {
        method.setHeader("Upgrade-Insecure-Requests", upgradeInsecureRequests);
        method.setHeader("Accept", accept);
        method.setHeader("Content-Type",contentType);
        method.setHeader("Accept-Encoding", acceptEncoding);
        method.setHeader("Accept-Language", acceptLanguage);
        method.setHeader("Cache-Control", cacheControl);
        method.setHeader("Connection", connection);
        method.setHeader("Origin", host);
        method.setHeader("User-Agent", userAgent);
        if (null != cookies && !cookies.trim().equals(""))
            method.setHeader("Cookie", cookies.substring(0, cookies.lastIndexOf(";")));
        if (null != referer) {
            method.setHeader("Referer", referer);
        }
    }
}
