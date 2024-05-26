package com.amswh.iLIMS.partner.service;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.partner.YQAPIClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 本类处理云鹊API交互接口功能
 */

@Service
public class YQService implements IPartner {

    private static String businessCategoryId = "8423469cd60f9fb85cd2907e7e98bcf3";

    // dev
    //private static String businessCategoryId = "df12e231f5eabfc469aa4a4b259ad414";

    static{
        HttpClientBuilderParams httpsParam = new HttpClientBuilderParams();
        // dev
        //httpsParam.setAppKey("204218020");
        //httpsParam.setAppSecret("RuAxVVYkf8dWXitr28SA10EZ6e5JxWr0");

        // prod
        httpsParam.setAppKey("204220644");
        httpsParam.setAppSecret("uYmYgrNZRSWfhVTAB7zQkzWAkCXnwOKm");
        YQAPIClient.getInstance().init(httpsParam);
    }
    @Override
    public Map<String, Object> fetchPatientInfo(String barCode) throws Exception {
        if(StringUtils.isEmpty(barCode)){
            return null;
        }
       try {

            ApiResponse response =  YQAPIClient.getInstance().getSampleInfoSyncMode(barCode , businessCategoryId);
            String json = new String(response.getBody(), SdkConstant.CLOUDAPI_ENCODING);
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);
            JsonNode codeNode = root.get("code");
            String code=String.valueOf(codeNode.asText());
            String message=root.get("message").asText();
            if("000000".equals(code)){
                JsonNode data=root.get("data");//开始解析获取的数据
                Map<String,Object> result=new HashMap<>();
                result.put("name",data.get("name").asText());
                result.put("gender",data.get("gender").asInt()==1?"M":"F");
                result.put("age",data.get("age").asInt());
                result.put("phone",data.get("mobile").asText());
                result.put("IdNumber",data.get("idNo").asText());
                result.put("samplingTime",data.get("sampleTime").asText());
                result.put("productCode",data.get("productNo").asText());
                result.put("birthDay",data.get("birthDay").asText());
                return result;

            }else if("400004".equals(code)){
                System.out.println("云鹊医:fetchPatientInf  barCode="+barCode+" "+message);
               // return  null;
            }
        } catch (Exception e) {
            System.out.println("云鹊医:fetchPatientInf error barCode="+barCode+" error:"+e.getMessage());
            throw new Exception("访问云鹊网络异常");
        }
        return  null;
    }

    @Override
    public String whoAmI() {
        return "YQ";
    }
}
