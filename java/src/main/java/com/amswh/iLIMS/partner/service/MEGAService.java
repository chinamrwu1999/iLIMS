package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.partner.IPartner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class MEGAService implements IPartner {

    String appKey="AMS3261618032001";
    String secretKey="68880e04a5d7482d980f3d18aec968f4";
    String domain="http://mstp.megagenomics.cn";//
    String url4PatientInfo="/api/v1.0/sample/getSample";//获取样本信息1（单个条码）
    String url4PatientInfoAtTime="/api/v1.0/sample/getSampleByTime";//获取样本信息2（指定时间段）
    String url4UpdateStatus="/api/v1.0/sample/sampleStatus";//更新样本状态接口
    String url4PushPDF="/api/v1.0/sample/sampleURLResult";//推送PDF报告URL
    @Override
    public Map<String, Object> fetchPatientInfo(String barCode) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("sample_code",barCode);
        String sign = this.signInput(map);
        String url = domain.concat(url4PatientInfo).concat("?")
                .concat("appid=".concat(appKey))
                .concat("&sample_code=".concat(barCode))
                .concat("&sign=".concat(sign));
//        HttpGet httpGet = new HttpGet(url);
//        HttpClient httpClient = HttpClients.createDefault();


        try {
          //  HttpResponse res = httpClient.execute(httpGet);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("content-type", "application/json;charset=UTF-8");
//            headers.set("user_token", this.token);
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String json = new String(response.getBody().toString().getBytes("ISO-8859-1"), "UTF-8");
                System.out.println(json);
                ObjectMapper objectMapper = new ObjectMapper();
               // Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private String signInput(Map<String, String> map){
        map.put("appid",appKey);
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(Objects.equals(val, "") || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }

            }
            result = sb.append("key=".concat(appKey)).toString();
            result = DigestUtils.md5Hex(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;

    }
}
