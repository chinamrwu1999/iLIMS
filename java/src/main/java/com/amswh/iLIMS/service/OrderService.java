package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.Order;
import com.amswh.iLIMS.domain.Product;
import com.amswh.iLIMS.mapper.lims.IOrder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OrderService extends ServiceImpl<IOrder, Order> {

    @Resource
    IOrder orderMapper;

    public Product getShippedProductInfo(String barCode){
        return  orderMapper.getOrderProduct(barCode);
    }

    public Map<String, Object> getOrderInfo(String barCode){
          return orderMapper.getOrderInfo(barCode);
    }
}