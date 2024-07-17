package com.amswh.iLIMS.project.service;


import com.amswh.iLIMS.project.domain.BarExpress;
import com.amswh.iLIMS.project.domain.Product;
import com.amswh.iLIMS.project.mapper.lims.IBarExpress;
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
