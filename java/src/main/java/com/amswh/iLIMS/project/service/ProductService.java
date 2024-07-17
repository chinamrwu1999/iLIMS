package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.Product;
import com.amswh.iLIMS.project.mapper.lims.IProduct;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService extends ServiceImpl<IProduct, Product> {

    public Map<String,String> listAnalyte2Product(){
          return this.baseMapper.getAnalyte2Product();
    }
}
