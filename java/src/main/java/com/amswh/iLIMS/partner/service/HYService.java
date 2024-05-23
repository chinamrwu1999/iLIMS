package com.amswh.iLIMS.partner.service;


import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.utils.MD5Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HYService implements IPartner {
    private  String sampleInfoUrl = "https://www.huanyatijianzx.com/PlugNew/huanYaAiMiShengAction/getPersonalInfoByCheckNoOrDate.action";
    @Override
    public Map<String, Object> fetchPatientInfo(String barCode) throws Exception {
        if(StringUtils.isEmpty(barCode)){
            return null;
        }
        try {
            String sign = getHySign();
            RestTemplate restTemplate = new RestTemplate();
            String url = sampleInfoUrl.concat("?sign=".concat(sign.toUpperCase())).concat("&checkNo=").concat(barCode);
           // Long userId = 1L; // Replace with your desired user ID
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {

                String responseBody = response.getBody();
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> resultMap = mapper.readValue(responseBody, Map.class);
                int state = (int) resultMap.get("STATE");
                if (state == 1) {
                    List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get("MSG");
                    if (dataList != null && !dataList.isEmpty()) {
                        Map<String, Object> src = dataList.get(0);
                        Map<String, Object> target = new HashMap<>();
                        target.put("name", String.valueOf(src.get("PERNAME")));
                        target.put("gender", String.valueOf(src.get("GENDER")));
                        if(src.get("AGE")!=null) {
                            target.put("age", Integer.parseInt(src.get("AGE").toString()));
                        }
                        target.put("phone", String.valueOf(src.get("TEL")));
                        target.put("birthDay", String.valueOf(src.get("BIRTHDAY")));
                        target.put("IdNumber", String.valueOf(src.get("CARDNUMBER")));
                        target.put("productCode", String.valueOf(src.get("ITEMID")));
                        target.put("samplingTime", String.valueOf(src.get("CHECKTIME")));
                        return target;
                    }
                } else if (state == 0) {
                    System.out.println("环亚体检:身份验证失败");
                    return null;
                }
            }else{
                System.out.println("环亚体检: 网络异常");
                return null;
            }
           }catch (Exception e){
            System.out.println("环亚：获取sampleInf 错误："+e.getMessage());
            return  null;

        }
         return null;
    }

      public static String getHySign() {
        String appId = "Hy2004c41e34489b8a136e1cd09691xx";
        String secret = "Hy20246f5-14b0-40f1-9dcd-f070cf2e3a31";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        return MD5Utils.hash(appId+secret+sdf.format(new Date()));
    }
}
