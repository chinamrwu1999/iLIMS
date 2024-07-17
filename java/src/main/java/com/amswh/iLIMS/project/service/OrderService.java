package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.Order;
import com.amswh.iLIMS.project.domain.Product;
import com.amswh.iLIMS.project.mapper.lims.IOrder;
import com.amswh.iLIMS.project.mapper.oa.OAMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OrderService extends ServiceImpl<IOrder, Order> {

    @Resource
    IOrder orderMapper;

    @Resource
    OAMapper oaMapper;

    public Product getShippedProductInfo(String barCode){
        return  orderMapper.getOrderProduct(barCode);
    }

    public Map<String, Object> getOrderInfo(String barCode){
          return orderMapper.getOrderInfo(barCode);
    }

    public String getOrderNo(String barCode){
        return orderMapper.getOrderNo(barCode);
    }

    public String queryCustomer(String barCode){
        String orderNo=getOrderNo(barCode);
        if(orderNo==null) return null;
        Map<String,Object> mp= oaMapper.querySaleOrder(orderNo);
        if(mp!=null){
            Object customer=mp.get("customerName");
            if(customer!=null) {
                return customer.toString();
            }
        }
        return null;
    }

    public List<Map<String,Object>> queryOrderItems(String barCode){
        String orderNo=getOrderNo(barCode);
        if(orderNo==null) return null;
        return oaMapper.queryOrderItems(orderNo);
    }

}