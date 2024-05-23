package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.utils.HttpClientHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;


@Service
public class PAJKService {

   String domain="https://hims-lims.pingan.com.cn/lis";
   String hospitalId="WHAMS";
    String password="WHAMS@123";


    public String fetchToken(){
        ObjectMapper objectMapper=new ObjectMapper();
        //JSONObject jsonObject = new JSONObject();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("hospitalId",hospitalId);
        node.put("password",password);

        String result = "";
        try {
            result = HttpClientHelper.doPostJson(this.domain, objectMapper.writeValueAsString(node));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // JSONObject resultObj = objectMapper.re(result);
       // return resultObj.getJSONObject("data").getString("token");
        return null;
    }
}
