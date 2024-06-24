package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.Product;
import com.amswh.iLIMS.mapper.lims.IProduct;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService extends ServiceImpl<IProduct, Product> {

    public Map<String,String> listAnalyte2Product(){
          return this.baseMapper.getAnalyte2Product();
    }
}
