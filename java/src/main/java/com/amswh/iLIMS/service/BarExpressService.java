package com.amswh.iLIMS.service;


import com.amswh.iLIMS.domain.BarExpress;
import com.amswh.iLIMS.domain.Product;
import com.amswh.iLIMS.mapper.lims.IBarExpress;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BarExpressService  extends ServiceImpl<IBarExpress, BarExpress> {

      public BarExpress getBarExpressByBarCode(String barCode){
           return baseMapper.getBarExpressByBarCode(barCode);
      }

      public Product getProductByBarCode(String barCode){
           return baseMapper.getProductByBarCode(barCode);
      }

}
