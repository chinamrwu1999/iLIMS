package com.amswh.iLIMS.partner.yq;


import com.alibaba.cloudapi.sdk.client.ApacheHttpClient;
import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.ParamPosition;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;

import java.util.List;
import java.util.Map;

public class YQAPIClient extends ApacheHttpClient{
    public final static String HOST = "gateway.yunqueyi.com";
    //public final static String HOST = "test-gateway.yunqueyi.com";
    static YQAPIClient instance = new YQAPIClient();
    public static YQAPIClient getInstance(){return instance;}

    public void init(HttpClientBuilderParams httpClientBuilderParams){
        httpClientBuilderParams.setScheme(Scheme.HTTPS);
        httpClientBuilderParams.setHost(HOST);
        super.init(httpClientBuilderParams);
    }



    public void syncSampleStatus(Map<String, List<String>> queryParams, Map<String, List<String>> headerParams, byte[] body , ApiCallback callback) {
        String path = "/diplomat/open/detection/sampleInfo/status";
        ApiRequest request = new ApiRequest(HttpMethod.PUT_BODY , path, body);
        request.setQuerys(queryParams);
        request.setHeaders(headerParams);
        request.setBody(body);
        sendAsyncRequest(request , callback);
    }

    public ApiResponse syncSampleStatusSyncMode(Map<String, List<String>> queryParams, Map<String, List<String>> headerParams, byte[] body) {
        String path = "/diplomat/open/detection/sampleInfo/status";
        ApiRequest request = new ApiRequest(HttpMethod.PUT_BODY , path, body);
        request.setQuerys(queryParams);
        request.setHeaders(headerParams);
        request.setBody(body);
        return sendSyncRequest(request);
    }
    public void getSampleInfo(String sampleCode , String businessCategoryId , ApiCallback callback) {
        String path = "/diplomat/open/detection/sampleInfo/[sampleCode]";
        ApiRequest request = new ApiRequest(HttpMethod.GET , path);
        request.addParam("sampleCode" , sampleCode , ParamPosition.PATH , false);
        request.addParam("businessCategoryId" , businessCategoryId , ParamPosition.QUERY , false);
        sendAsyncRequest(request , callback);
    }


    public ApiResponse getSampleInfoSyncMode(String sampleCode , String businessCategoryId) {
        String path = "/diplomat/open/detection/sampleInfo/[sampleCode]";
        ApiRequest request = new ApiRequest(HttpMethod.GET , path);
        request.addParam("sampleCode" , sampleCode , ParamPosition.PATH , false);
        request.addParam("businessCategoryId" , businessCategoryId , ParamPosition.QUERY , false);
        return sendSyncRequest(request);
    }


}
