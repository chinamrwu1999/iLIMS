package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.Product;
import com.amswh.iLIMS.mapper.lims.IProduct;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends ServiceImpl<IProduct, Product> {
}
