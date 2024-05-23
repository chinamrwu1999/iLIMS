package com.amswh.iLIMS.utils;



import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClientHelper {

    /**
     * HttpClient
     */
    private static CloseableHttpClient client = null;

    /**
     * 请求超时时间
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 60 * 1000;

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 300 * 1000;

    /**
     * 读取超时时间
     */
    private static final int SOCKET_TIMEOUT = 60 * 1000;

    /**
     * 线程池最大连接数
     */
    private static final int MAX_TOTAL = 1000;

    /**
     * 单个路由最大连接数
     */
    private static final int MAX_PER_ROUTE = 100;

    /**
     * 初始化 HTTPClient
     *
     * @return CloseableHttpClient
     */
    private static synchronized CloseableHttpClient getHttpClient() {
        if (client == null) {
            ConnectionSocketFactory connectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory layeredConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", connectionSocketFactory).register("https", layeredConnectionSocketFactory).build();
            //HttpClient 连接池
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
            poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
            //Request配置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).build();
            client = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig).build();
        }
        return client;
    }

    /**
     * Response回调函数
     */
    private static ResponseHandler<String> responseHandler = response -> {
        //状态码200时返回响应值
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    };


    /**
     * Get请求
     *
     * @param url 请求路径+参数
     * @return String
     */
    public static String doGet(String url) throws IOException {
        client = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        return client.execute(httpGet, responseHandler);
    }

    /**
     * Get请求
     *
     * @param url    请求路径
     * @param params 参数Map集合
     * @return String
     */
    public static String doGet(String url, Map<String, String> params) throws URISyntaxException, IOException {
        client = getHttpClient();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (null != params) {
            for (String key : params.keySet()) {
                uriBuilder.addParameter(key, params.get(key));
            }
        }
        URI uri = uriBuilder.build();
        HttpGet httpGet = new HttpGet(uri);
        return client.execute(httpGet, responseHandler);
    }

    /**
     * Form表单提交
     *
     * @param url    请求地址
     * @param params 参数
     * @return String
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        if (null != params) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (String key : params.keySet()) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
        }
        return client.execute(httpPost, responseHandler);
    }

    /**
     * 发送Json数据
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String doPostJson(String url, String jsonStr) throws IOException {
        client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
        return client.execute(httpPost, responseHandler);
    }

    /**
     * 发送PUT Json数据
     * @param url
     * @param jsonStr
     * @return
     * @throws IOException
     */
    public static String doPutJson(String url, String jsonStr) throws IOException{
        // System.out.println(jsonStr);
        client = getHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
        return client.execute(httpPut, responseHandler);
    }

    /**
     * 发送Soap数据
     *
     * @param url     请求地址
     * @param soapStr soap封装格式字符串
     * @return Soap XML格式
     */
    public static String doPostSoap(String url, String soapStr) throws IOException {
        client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
        httpPost.setEntity(new StringEntity(soapStr, ContentType.APPLICATION_SOAP_XML));
        return client.execute(httpPost, responseHandler);
    }

}

