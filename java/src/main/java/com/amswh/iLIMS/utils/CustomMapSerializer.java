package com.amswh.iLIMS.utils;


/**
 *  将Map转换为JSON对象时，定制化keyName
 */
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Map;

public class CustomMapSerializer extends JsonSerializer<Map<String, Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper(); // Create a single ObjectMapper instance

    @Override
    public void serialize(Map<String, Object> map, JsonGenerator jsonGen, SerializerProvider serializers) throws IOException {
        ObjectNode objectNode = objectMapper.createObjectNode(); // Use ObjectMapper to create ObjectNode

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = transformKey(entry.getKey());

            objectNode.set(key, objectMapper.valueToTree(entry.getValue()));
        }

        jsonGen.writeTree(objectNode);
    }

    private String transformKey(String key) {
        if("barcode".equalsIgnoreCase(key)) return "barCode";
        if("analytecode".equalsIgnoreCase(key)) return "analyteCode";
        if("expressno".equalsIgnoreCase(key)) return "expressNo";
        if("partnercode".equalsIgnoreCase(key)) return "partnerCode";
        if("partnername".equalsIgnoreCase(key)) return "partnerName";
        if("idnumber".equalsIgnoreCase(key)) return "IDNumber";
        if("idtype".equalsIgnoreCase(key)) return "IDType";
        if("createtime".equalsIgnoreCase(key)) return "createTime";

        return key;
    }
}

