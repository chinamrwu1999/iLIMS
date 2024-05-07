package com.amswh.MYLIMS.partner.rh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iss.iescp.web.sign.sdk.bas.SignClientConstant;
import com.iss.iescp.web.sign.sdk.bas.SignServerException;
import com.iss.iescp.web.sign.sdk.client.PcisServiceClient;
import com.iss.iescp.web.sign.sdk.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.TreeMap;


@Service
public class RHService {



    /*
    根据保单号查询保险状态，以确定检测申请者是否有检测权益

     */
    public void fetchServiceStatus(String bizCode){
        PcisServiceClient sdkClient=new PcisServiceClient("https://apidat.rhassurance.com","secret_id_dat20240506whams001","secret_key_dat20240506whams001");
        ObjectMapper objectMapper=new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("CBizId", "62400498308");
        jsonNode.put("COrganCode", "202400000003");

        // Convert ObjectNode to JSON string
        try {
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            String json= sdkClient.callService("HealthService.qryHealthServiceState",jsonString);
            System.out.println(json);

        } catch (Exception e) {
             e.printStackTrace();
            //throw new RuntimeException(e);
        }

    }

    /*
       服务使用记录回传接口

   */
    public  void pushTestResult(String bizCode){

    }


}
