package com.amswh.iLIMS.oa;


import com.amswh.iLIMS.mapper.oa.OAMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAQueryService {

    @Resource
    OAMapper oaMapper;

    public Map<String,Object> queryOrderInfo(String orderNo){
        return oaMapper.querySaleOrder(orderNo);
    }

    public List<Map<String,Object>> queryOrderItems(String orderNo){
         return oaMapper.queryOrderItems(orderNo);
    }


}
