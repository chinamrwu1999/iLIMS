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

    public String queryOACustomer(String orderNo){
          return this.oaMapper.queryOACustomer(orderNo);
    }

    public Map<String,Object> queryOAOrder(String orderNo){
        return  this.oaMapper.queryOAOrder(orderNo);
    }

    public List<Map<String,Object>> queryOAOrderItems(String orderNo){
         return this.oaMapper.queryOAOrderItems(orderNo);
    }


}
