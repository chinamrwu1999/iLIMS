package com.amswh.iLIMS.partner.service;


/*
     西南医投接口服务，完成以下功能：
       1） 身份认证，登录对方系统并获取身份token
       2） 根据体检码，从对方系统拉取受检者信息
 */


import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.utils.MyStringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class XNYTService implements IPartner {

    private String domain="http://61.188.205.4:4864";
    private String token=null;
    private LocalDateTime tokenTime=null;
    /*
        根据条码号从对方获取受检者信息
        http方法：GET
        参数：barCode 条码号
        返回:Map
     */
    @Override
    public PatientInfo fetchPatientInfo(String barCode) throws Exception {
        try {
            this.updateToken();
            if (this.isValidToken()) {
                UriComponentsBuilder URLBuilder = UriComponentsBuilder.fromHttpUrl(String.format("%s/zj-peis/peisApi/getPersonInfoByLabel", this.domain))
                        .queryParam("labelSn", barCode);
                String url = URLBuilder.toUriString();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("content-type", "application/json;charset=UTF-8");
                headers.set("user_token", this.token);
                HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    String json = new String(response.getBody().toString().getBytes("ISO-8859-1"), "UTF-8");
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);

                   int state=(int)jsonMap.get("resultCode");
                   if(state==1){
                            Map<String, Object> src = (Map<String, Object>) jsonMap.get("result");
                            if(!(src==null || src.isEmpty())){
                                PatientInfo patient=new PatientInfo(barCode,src.get("name").toString());
                                patient.setGender("男".equals(src.get("genderCode"))? "M" : "F");
                                if(src.get("age")!=null){
                                    patient.setAge((Integer) src.get("age"));
                                }
                                if(src.get("tel")!=null){
                                    patient.setPhone(src.get("tel").toString());
                                }
                                if(src.get("birthday")!=null){
                                    patient.setBirthDate(src.get("birthday").toString());
                                }
                                if(!MyStringUtils.isEmpty(src.get("idCardNum"))){
                                    patient.setIDNumber(src.get("idCardNum").toString());
                                }
                                if(!MyStringUtils.isEmpty(src.get("projectCode"))){  // 注意这里返回的是AD06
                                    patient.setProductCode(src.get("projectCode").toString());
                                }else{
                                    patient.setProductCode("LDT06");
                                }
                                if(!MyStringUtils.isEmpty(src.get("homeAddress")!=null)){
                                    patient.setOtherFieldInfo("address",src.get("homeAddress"));
                                }
                                return patient;
                            }
                        }
                    }

                }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    @Override
    public String whoAmI() {
        return "XNYT";
    }

    /*
         登录西南医投LIS系统，获取token，为后续api交互authentication
     */
    private void fetchToken() throws  Exception {

        String username="whams";
        String password="whams@123";
        UriComponentsBuilder URLBuilder = UriComponentsBuilder.fromHttpUrl(String.format("%s/zj-peis/j_login",this.domain))
                .queryParam("j_username", username)
                .queryParam("j_password",password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("content-type","application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity( URLBuilder.toUriString(), requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String data = response.getBody();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readValue(data, JsonNode.class);
                JsonNode resultNode=rootNode.get("result");
                JsonNode refreshNode=resultNode.get("refresh_token");
                JsonNode userTokenNode=resultNode.get("user_token");
                if(userTokenNode!=null){
                    String userToken=userTokenNode.asText();
                    if(userToken!="" && userToken.length()>10){
                        this.token=userToken;

                    }
                }
                this.tokenTime=LocalDateTime.now();

            } catch (Exception e) {
                throw e;

            }

        } else {
       throw  new Exception("西南医投:获取认证token发生网络异常");
        }



    }
    private boolean isValidToken(){
        if(token==null) return false;
        Duration difference = Duration.between(this.tokenTime,LocalDateTime.now());
        return  difference.compareTo(Duration.ofHours(6)) < 0;
    }

    public void updateToken() throws  Exception{
        if(!this.isValidToken()){
            this.fetchToken();
        }
    }


}
