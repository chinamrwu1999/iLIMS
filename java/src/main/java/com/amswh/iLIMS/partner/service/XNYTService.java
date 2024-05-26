package com.amswh.iLIMS.partner.service;


/*
     西南医投接口服务，完成以下功能：
       1） 身份认证，登录对方系统并获取身份token
       2） 根据体检码，从对方系统拉取受检者信息
 */


import com.amswh.iLIMS.partner.IPartner;
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
    public Map<String, Object> fetchPatientInfo(String barCode) throws Exception {
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
                                Map<String, Object> target = new HashMap<>();
                                target.put("name", String.valueOf(src.get("name")));
                                target.put("gender", "男".equals(String.valueOf("genderCode") )? "M" : "F");
                                target.put("age", (int)src.get("age"));
                                target.put("phone", String.valueOf(src.get("tel")));
                                target.put("birthDay", String.valueOf(src.get("birthday")));
                                target.put("IdNumber", String.valueOf(src.get("idCardNum")));
                                target.put("productCode", String.valueOf(src.get("projectCode")));
                                target.put("barCode", String.valueOf(src.get("num")));
                                target.put("address", String.valueOf(src.get("homeAddress")));
                                return target;
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
                        // System.out.println("get user_token:"+this.token);
                    }
                }
                this.tokenTime=LocalDateTime.now();
                //System.out.println("西南医投：获取用户token 成功！");
            } catch (Exception e) {
                throw e;

            }

        } else {

            System.out.println("Error: " + response.getStatusCodeValue());
            throw  new Exception("获取西南医投认证token发生网络异常");
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
