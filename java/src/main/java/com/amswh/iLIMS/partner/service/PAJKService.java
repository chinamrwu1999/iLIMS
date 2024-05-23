package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.utils.HttpClientHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;


@Service
public class PAJKService implements IPartner {

   String domain="https://hims-lims.pingan.com.cn/lis";
   String hospitalId="WHAMS";
    String password="WHAMS@123";

    String token=null;


    private void fetchToken(){
        try {
        ObjectMapper objectMapper=new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("hospitalId",hospitalId);
        node.put("password",password);
        String json=objectMapper.writeValueAsString(node);

        String result = HttpClientHelper.doPostJson(this.domain.concat("/api/signtoken/create_Middle"), json);
        JsonNode rootNode = objectMapper.readValue(result, JsonNode.class);
        if(rootNode.get("success").asBoolean()){
            JsonNode dataNode=rootNode.get("data");
            this.token=dataNode.get("token").asText();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<String, Object> fetchPatientInfo(String barCode) throws Exception {
        if(this.token==null) {
            this.fetchToken();
        }
        ObjectMapper objMapper=new ObjectMapper();
        ObjectNode node = objMapper.createObjectNode();
        node.put("barCode",barCode);
        node.put("hospitalId",hospitalId);
        node.put("labCenterCode","101001");
        String result = "";
        try {

            RestTemplate restTemplate = new RestTemplate();
            String url =domain.concat("/api/sample/querydelegateinfobybarcode_middle");
            String requestBody = objMapper.writeValueAsString(node);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange( url, HttpMethod.POST, requestEntity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                try {
                     System.out.println("\n"+responseEntity.getBody()+"\n");
                     JsonNode jsonNode=objMapper.readTree(responseEntity.getBody());
                     if(jsonNode.get("success").asBoolean()) {
                        // Map<String, Object> responseBodyMap = objMapper.readValue(jsonNode.get("data"), );
                        // responseBodyMap.forEach((key, value) -> System.out.println(key + ": " + value));
                     }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            }



        } catch (Exception e) {
           e.printStackTrace();
        }
        return  null;

    }
}
