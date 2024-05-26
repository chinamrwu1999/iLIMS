package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.utils.HttpClientHelper;
import com.amswh.iLIMS.utils.MyStringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
                     JsonNode jsonNode=objMapper.readTree(responseEntity.getBody());
                     if(jsonNode.get("success").asBoolean()) {

                        JsonNode arrayNode=jsonNode.get("data");
                        JsonNode data= arrayNode.get(0);
                        Map<String,Object> tmpMap=objMapper.readValue(objMapper.writeValueAsString(data),Map.class);
                        if(tmpMap!=null && !tmpMap.isEmpty()) {
                            Map<String,Object> result=new HashMap<>();
                            if (!MyStringUtils.isEmpty(tmpMap.get("patientName"))) {
                                result.put("name", tmpMap.get("patientName"));
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("sexName"))) {
                                result.put("gender", "女".equals(tmpMap.get("sexName").toString()) ? "F" : "M");
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("age"))) {
                                result.put("age", Integer.parseInt(tmpMap.get("age").toString()));
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("patientPhone"))) {
                                result.put("phone", tmpMap.get("patientPhone"));
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("sampleCollectionTime"))) {
                                result.put("samplingTime", tmpMap.get("sampleCollectionTime").toString().replace("T"," "));
                            }

                            JsonNode node1=data.get("applySampleItem").get(0);
                            if(node1!=null){
                                if(node1.get("applyItemCode")!=null){
                                    String itemCode= node1.get("applyItemCode").asText();
                                    if("UN220843".equals(itemCode)){
                                        result.put("productCode", "LDT06");
                                    }
                                }
                            }
                            JsonNode d1 = objMapper.readTree(data.get("pingAnRequestJson").asText());
                            d1 = d1.get("data").get(0);
                            tmpMap = objMapper.readValue(objMapper.writeValueAsString(d1), Map.class);
                            if(tmpMap!=null && !tmpMap.isEmpty()) {
                                if (!MyStringUtils.isEmpty(tmpMap.get("papersNo"))) {
                                    result.put("IDNumber", tmpMap.get("papersNo"));
                                }
                                if (!MyStringUtils.isEmpty(tmpMap.get("papersType"))) {
                                    result.put("IDType", tmpMap.get("papersType"));
                                }
                                if (!MyStringUtils.isEmpty(tmpMap.get("birthday"))) {
                                    String s1=tmpMap.get("birthday").toString();
                                    if(s1.length()==8){
                                        s1=s1.substring(0,4).concat("-").concat(s1.substring(4,6)).concat("-").concat(s1.substring(6,8));
                                    }
                                    result.put("birthDay",s1);
                                }
                                if (!MyStringUtils.isEmpty(tmpMap.get("address"))) {
                                    result.put("address", tmpMap.get("address"));
                                }
                            }
                            return result;
                        }
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

    @Override
    public String whoAmI() {
        return "PAJK";
    }
}
