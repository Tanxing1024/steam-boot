package com.stupidzhe.api.domain;

import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.util.HttpUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.W on 2017/5/24.
 * HTTP请求
 */
public class Http implements Serializable {

    private static final long serialVersionUID = -222222222L;

    private static final Logger log = LoggerFactory.getLogger(Http.class);

    public HttpBean request(String url, String methodType, Map<String, String> data, String cookies, boolean timeOut, String referer, boolean redirect) {
        HttpBean res = new HttpBean();
        List<NameValuePair> postData = new LinkedList<>();
        if (null != data && 0 < data.size()) {
            StringBuilder dataString = new StringBuilder();
            if (methodType.equalsIgnoreCase(HttpUtil.METHOD_GET)) {
                for (final String key : data.keySet()) {
                    dataString.append(key).append("=").append(data.get(key)).append("&");
                }
                url += "?" + dataString.substring(0, dataString.length() - 1);
            } else if (methodType.equalsIgnoreCase(HttpUtil.METHOD_POST)) {
                for (final String key : data.keySet()) {
                    postData.add(new BasicNameValuePair(key, data.get(key)));
                }
            } else {
                return res;
            }
        }
        int statusCode;
        String content;
        RequestConfig requestConfig;
        RequestConfig.Builder builder = RequestConfig.custom();
        if (timeOut) {
            builder = builder
                    .setConnectTimeout(60000).setConnectionRequestTimeout(30000)
                    .setSocketTimeout(80000);
        }
        if (redirect) {
            builder.setCircularRedirectsAllowed(false).setRedirectsEnabled(false).
                    setRelativeRedirectsAllowed(false);
        }
        requestConfig = builder.build();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            if (methodType.equalsIgnoreCase(HttpUtil.METHOD_GET)) {
                HttpGet method = new HttpGet(url);

                HttpUtil.addHeader(method, cookies, referer);
                method.setConfig(requestConfig);

                CloseableHttpResponse response = httpClient.execute(method);
                content = HttpUtil.getContent(response.getEntity().getContent());
                statusCode = response.getStatusLine().getStatusCode();
                handleResponse(statusCode, res, response);
                response.close();
            } else {
                HttpPost method = new HttpPost(url);
                HttpUtil.addHeader(method, cookies, referer);
                method.setEntity(new UrlEncodedFormEntity(postData, Consts.UTF_8));
                method.setConfig(requestConfig);

                CloseableHttpResponse response = httpClient.execute(method);
                content = HttpUtil.getContent(response.getEntity().getContent());
                statusCode = response.getStatusLine().getStatusCode();
                handleResponse(statusCode, res, response);
                response.close();
            }
            res.setCode(statusCode);
            res.setResponse(content);
            res.setTimeOut(false);
            res.setSessionInvalid(false);
            return res;
        } catch (SocketTimeoutException e) {
            log.error("读取超时");
            res.setTimeOut(true);
            return res;
        } catch (ConnectTimeoutException e) {
            log.error("连接超时");
            res.setTimeOut(true);
            return res;
        } catch (ClientProtocolException e) {
            log.error("session失效");
            res.setSessionInvalid(true);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("http IOException");
            res.setSessionInvalid(true);
            return res;
        }
    }

    private static void handleResponse(int statusCode, HttpBean res, HttpResponse response) {
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||
                statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            // 从头中取出转向的地址
            Header locationHeader = response.getFirstHeader("location");
            String location;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                log.error("The page was redirected to:" + location);
            } else {
                log.error("Location field value is null.");
            }
        }
        Header[] header = response.getHeaders("Set-Cookie");
        if (null != header && 0 < header.length) {
            StringBuilder getCookies = new StringBuilder();

            for (Header h : header) {
                HeaderElement[] elements = h.getElements();
                for (HeaderElement element : elements) {
                    getCookies.append(element.getName()).append("=").append(element.getValue()).append("; ");
                    if ("sessionid".equals(element.getName())) {
                        res.setSession(element.getValue());
                    }
                }
            }
            res.setCookies(getCookies.toString());
        } else {
            res.setCookies(null);
        }
    }


}