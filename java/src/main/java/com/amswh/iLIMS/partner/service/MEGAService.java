package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.partner.IPartner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.Period;
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
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("content-type", "application/json;charset=UTF-8");
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String json = new String(response.getBody().toString().getBytes(), "UTF-8");
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root=objectMapper.readValue(json,JsonNode.class);
                String code=root.get("code").asText();
                if("SUCCESS".equals(code)){
                    JsonNode array=root.get("data");
                    if(array!=null && !array.isEmpty()){
                        JsonNode data=array.get(0);
                        String dataJson=objectMapper.writeValueAsString(data);
                        Map<String,Object> tmpMap=objectMapper.readValue(dataJson,Map.class);
                        if(tmpMap!=null && !tmpMap.isEmpty()){
                            Map<String,Object> result=new HashMap<>();
                            result.put("agentId",tmpMap.get("agent_num"));//门店代码
                            result.put("name",tmpMap.get("username"));
                            result.put("gender","男".equals(tmpMap.get("gender").toString())?"M":"F");
                           // result.put("package",tmpMap.get("package_num"));
                            result.put("birthday",tmpMap.get("birthdate"));
                            result.put("samplingTime",tmpMap.get("date_sampling"));
                            result.put("phone",tmpMap.get("phone"));
                            result.put("barCode",tmpMap.get("barcode"));
                            if(tmpMap.get("birthdate")!=null){
                                LocalDate birthday = LocalDate.parse(tmpMap.get("birthdate").toString());
                                LocalDate currentDate = LocalDate.now();
                                Period period = Period.between(birthday, currentDate);
                                result.put("age",period.getYears());
                            }
                            if("CCF001E".equals(tmpMap.get("package_num").toString())){
                                result.put("productCode","LDT01");
                            }
                            return result;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public String whoAmI() {
        return "MEGA";
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
            result = sb.append("key=".concat(this.secretKey)).toString();
            result = DigestUtils.md5Hex(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;

    }
}
