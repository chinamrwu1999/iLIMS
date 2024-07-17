package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.amswh.iLIMS.framework.security.service.SysMenuService;
import com.amswh.iLIMS.project.service.ExpAnalyteService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/test")
public  class TestController {

   @Resource
   ExpAnalyteService service;

   @Resource
   SysMenuService menuService;


   // @PostMapping("/service")
    @GetMapping("/hello")
    @Transactional(transactionManager="limsTransactionManager")
    public AjaxResult TestMe(){
    try {

        return AjaxResult.success(menuTreeTest());


    }catch (Exception err){
        err.printStackTrace();
    }
    return  AjaxResult.error("发现错误哦");
   }

   private  List<SysMenu> menuTreeTest(){
       return menuService.getUserMenu(1L);
      // return null;
   }
}
