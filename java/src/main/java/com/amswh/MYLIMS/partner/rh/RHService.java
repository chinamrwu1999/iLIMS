package com.amswh.MYLIMS.partner.rh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
        jsonNode.put("CBizId", bizCode);
        jsonNode.put("COrganCode", "202400000003");
        jsonNode.put("CProtocolNo","");
        jsonNode.put("TChangeTm","");

        // Convert ObjectNode to JSON string
        try {
            String jsonString = objectMapper.writeValueAsString(jsonNode);

            String json= sdkClient.callService("HealthService.qryHealthServiceState",jsonString);
            JsonNode node = objectMapper.readTree(json);
            JsonNode dataNode = node.get("data");
            System.out.println("\n");
            if (dataNode.isArray()) {
                JsonNode dn=dataNode.get(0);
                System.out.println("CBizId:"+dn.get("CBizId").asText());
                System.out.println("CContStatus:"+dn.get("CContStatus").asText());
                System.out.println("CMasterProtocolNo:"+dn.get("CMasterProtocolNo").asText());
                System.out.println("CProtocolNo:"+dn.get("CProtocolNo").asText());
            }


            System.out.println("\n\n"+json+"\n <<<");

        } catch (Exception e) {
             e.printStackTrace();
            //throw new RuntimeException(e);
        }

    }

    /*
       服务使用记录回传接口

   */
    public  void pushTestResult(){
        PcisServiceClient sdkClient=new PcisServiceClient("https://apidat.rhassurance.com","secret_id_dat20240506whams002","secret_key_dat20240506whams002");
        ObjectMapper objectMapper=new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        //以下为非空项
        jsonNode.put("CDataSrc","JG0001");//数据来源
        jsonNode.put("CInterfaceSerialNo","RHPUSH01");//接口调用流水号;
        jsonNode.put("CSupplierNo","202400000003");//供应商编码
        jsonNode.put("CSupplierAppNo","testApp0001");//供应商申请单号
        jsonNode.put("CPlyNo","62400498308");// 保单号
        jsonNode.put("CMasterProtocolNo","TC2024042500000052");//总协议号
        jsonNode.put("CProtocolNo","C2024042500000320");//协议号
        jsonNode.put("CUserName","张三丰");//使用人姓名
        jsonNode.put("CUserMobile","15172509888");//手机
        jsonNode.put("CUserCertfCls","1");//使用人证件类型
        jsonNode.put("CUserCertfCde","423609099808981111");  //使用人证件号码
        jsonNode.put("CAppUserRl","1");//申请人与使用人关系
        jsonNode.put("CHealProjectCode","P202400006"); //健管产品编码
        jsonNode.put("CHealServiceCode","S00048");//健管服务编码
        jsonNode.put("CHealRightsCode","R00065");//健管权益编码
        jsonNode.put("CAppStatus","1");//申请单状态


//        jsonNode.put("CUserId","");
//        jsonNode.put("CAppName","");
//        jsonNode.put("CAppMobile","");
//        jsonNode.put("CProvince","");
//        jsonNode.put("CCity","");
//        jsonNode.put("CCounty","");
//        jsonNode.put("COrganCode","");
//        jsonNode.put("CAddrDetail","");
//        jsonNode.put("TAppTm","");
//        jsonNode.put("TCancelTm","");
//        jsonNode.put("TFinishTm","");

//        jsonNode.put("CAppTyp","");
//        jsonNode.put("NInvalidNum","");
//        jsonNode.put("NResidualNum","");
//        jsonNode.put("CCountUsage","");
//        jsonNode.put("CSatisfactionScore","");
//        jsonNode.put("CFavourBiz","");
//        jsonNode.put("NMinConsumeAmount","");
//        jsonNode.put("CFavourType","");
//        jsonNode.put("NFavourAmount","");

        // Convert ObjectNode to JSON string
        try {
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            String json= sdkClient.callService("HealthService.supplierHealthUse",jsonString);
//            JsonNode node = objectMapper.readTree(json);
//            JsonNode dataNode = node.get("data");
//            if (dataNode.isArray()) {
//                JsonNode dn=dataNode.get(0);
//                System.out.println(dn.get("CBizId").asText());
//                System.out.println(dn.get("CContStatus").asText());
//                System.out.println(dn.get("CMasterProtocolNo").asText());
//                System.out.println(dn.get("CProtocolNo").asText());
//            }


            System.out.println("\n\n"+json+"\n <<<");

        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }


    }


}
