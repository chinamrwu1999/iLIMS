package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.utils.HttpClientHelper;
import com.amswh.iLIMS.utils.MyStringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public PatientInfo fetchPatientInfo(String barCode) throws Exception {
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
                            //Map<String,Object> result=new HashMap<>();
                            PatientInfo patient=new PatientInfo(barCode,String.valueOf(tmpMap.get("patientName")));

                            if (!MyStringUtils.isEmpty(tmpMap.get("sexName"))) {
                                patient.setGender("女".equals(tmpMap.get("sexName").toString()) ? "F" : "M");
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("age"))) {
                                patient.setAge(Integer.parseInt(tmpMap.get("age").toString()));
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("patientPhone"))) {
                                patient.setPhone(tmpMap.get("patientPhone").toString());
                            }
                            if (!MyStringUtils.isEmpty(tmpMap.get("sampleCollectionTime"))) {
                                patient.setSamplingTime( tmpMap.get("sampleCollectionTime").toString().replace("T"," "));

                            }

                            JsonNode node1=data.get("applySampleItem").get(0);
                            if(node1!=null){
                                if(node1.get("applyItemCode")!=null){
                                    String itemCode= node1.get("applyItemCode").asText();
                                    if("UN220843".equals(itemCode)){
                                        patient.setProductCode("LDT06");

                                    }
                                }
                            }
                            JsonNode d1 = objMapper.readTree(data.get("pingAnRequestJson").asText());
                            d1 = d1.get("data").get(0);
                            tmpMap = objMapper.readValue(objMapper.writeValueAsString(d1), Map.class);
                            if(tmpMap!=null && !tmpMap.isEmpty()) {
                                if (!MyStringUtils.isEmpty(tmpMap.get("papersNo"))) {
                                    patient.setIDNumber(tmpMap.get("papersNo").toString());

                                }
                                if (!MyStringUtils.isEmpty(tmpMap.get("papersType"))) {
                                    patient.setIDType(tmpMap.get("papersType").toString());
                                }
                                if (!MyStringUtils.isEmpty(tmpMap.get("birthday"))) {
                                    String s1=tmpMap.get("birthday").toString();
                                    if(s1.length()==8){
                                        s1=s1.substring(0,4).concat("-").concat(s1.substring(4,6)).concat("-").concat(s1.substring(6,8));
                                    }
                                    patient.setBirthday(s1);
                                   // result.put("birthDay",s1);
                                }
                                if (!MyStringUtils.isEmpty(tmpMap.get("address"))) {
                                    patient.setOtherFieldInfo("address",tmpMap.get("address"));
                                }
                            }
                            return patient;
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
