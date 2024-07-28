package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.partner.PatientInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *  处理武汉艾迪康数据交互事务
 */

@Service
public class ADCService implements IPartner {

    String domain="https://dataexchange.adicon.com.cn:9281";
    String userName="A2023Ma6cszth.ad";
    String password="C2yy1e1504c98fb1baHztr.b46a11n";

    String token=null;
    private LocalDateTime tokenTime=null;

    @Override
    public PatientInfo fetchPatientInfo(String barCode) throws Exception {
          try {
              this.updateToken();
              if (this.isValidToken()) {
                  ObjectMapper objectMapper=new ObjectMapper();
                  ObjectNode paramNode = objectMapper.createObjectNode();
                  paramNode.put("barCode", barCode);
                  paramNode.put("orgcode", "1014");
                  HttpPost httpPost = new HttpPost(domain.concat("/api/lims/sampleinfo/get"));
                  httpPost.setEntity(new StringEntity( objectMapper.writeValueAsString(paramNode), ContentType.APPLICATION_JSON));
                  httpPost.setHeader("Authorization","Bearer ".concat(token));
                  String  response = getHttpsClient().execute(httpPost,responseHandler);

                  JsonNode responseNode=objectMapper.readTree(response);
                  if(responseNode!=null && responseNode.get("code").asInt()==200){
                         JsonNode data=responseNode.get("data");
                         int total=responseNode.get("total").asInt();

                         JsonNode node=null;
                         String nodeStr=null;
                         if(total>0) {
                             PatientInfo patient=new PatientInfo(barCode,data.get("patientname").asText());
                             if(data.get("sex")!=null){
                                 String val=data.get("sex").asText();
                                 patient.setGender("男".equals(val)? "M" : "F");
                             }
                            node=data.get("age");
                             if(node!=null) {
                                 nodeStr=node.asText();
                                 if (nodeStr != null && nodeStr.indexOf("^岁") > 0) {
                                     patient.setAge( Integer.parseInt(nodeStr.substring(0, nodeStr.indexOf("^岁"))));
                                 }
                             }
                             node=data.get("patienttel");
                             if(node!=null) {
                                 nodeStr=node.asText();
                                 if (nodeStr != null ) {
                                     patient.setPhone(nodeStr.trim());
                                    }
                             }
                             node=data.get("sampletime");
                             if(node!=null) {
                                 nodeStr=node.asText();
                                 if (nodeStr != null ) {
                                     patient.setSamplingTime(nodeStr.trim());
                                 }
                             }
                             node=data.get("testitemcode");
                             if(node!=null) {
                                 nodeStr=node.asText();
                                 if (nodeStr != null && "Q4417".equals(nodeStr.trim()) ) {
                                     patient.setProductCode("LDT01");
                                 }
                             }
                             node=data.get("hospitalbarcode");
                             if(node!=null) {
                                 nodeStr=node.asText();
                                 if (nodeStr != null ) {
                                     patient.setOtherFieldInfo("hosipitalBarCode",nodeStr.trim());
                                 }
                             }
                             return patient;
                         }
                     }
                  }
          }catch (Exception err){
             // err.printStackTrace();
          }
            return null;
    }
    @Override
    public String whoAmI() {
        return "ADC";
    }

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

    private boolean isValidToken(){
        if(token==null) return false;
        Duration difference = Duration.between(this.tokenTime,LocalDateTime.now());
        return  difference.compareTo(Duration.ofHours(1)) < 0;
    }

    public void updateToken() throws  Exception{
        if(!this.isValidToken()){
            this.fetchToken();
        }
    }

    public static CloseableHttpClient getHttpsClient() {

        CloseableHttpClient httpClient;
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        } catch (KeyManagementException e) {
            e.getStackTrace();
        } catch (KeyStoreException e) {
            e.getStackTrace();
        }
        httpClient = HttpClients.custom().setSSLContext(sslContext).
                setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        return httpClient;
    }



    private void fetchToken() throws Exception {
        HttpPost httpPost = new HttpPost(domain.concat("/api/oauth/gettoken?userName=".concat(userName)+"&userPwd=".concat(password)));
        String response = getHttpsClient().execute(httpPost,responseHandler);
        ObjectMapper objectMapper=new ObjectMapper();
       JsonNode responseNode=objectMapper.readTree(response);
        if(responseNode!=null && responseNode.get("code").asInt()==200){
            JsonNode dataNode=responseNode.get("data");
            if(dataNode!=null){
                this.token=dataNode.get("access_token").asText();
                this.tokenTime=LocalDateTime.now();
            }else{
                System.out.println("武汉艾迪康:获取身份token认证失败");
            }
        }else{
            System.out.println("武汉艾迪康:获取身份token认证时网络异常");
        }
    }



}
