package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.model.SysKeys;
import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.amswh.iLIMS.framework.security.service.JoseJWTService;
import com.amswh.iLIMS.framework.security.service.SysKeyService;
import com.amswh.iLIMS.framework.security.service.SysMenuService;
import com.amswh.iLIMS.framework.security.service.SysUserService;
import com.amswh.iLIMS.framework.utils.RSAUtils;
import com.amswh.iLIMS.project.service.ExpAnalyteService;
import jakarta.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.jose4j.jwt.JwtClaims;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.security.Key;
import java.security.KeyPair;
import java.util.List;


@RestController
@RequestMapping("/test")
public  class TestController {

   @Resource
   ExpAnalyteService service;

   @Resource
   SysMenuService menuService;

   @Resource
    JoseJWTService tokenService;

   @Resource
   SysKeyService keyService;

   @Resource
    SysUserService userService;


   // @PostMapping("/service")
    @GetMapping("/hello")
    @Transactional(transactionManager="limsTransactionManager")
    public AjaxResult TestMe(){
    try {
        System.out.println("calling test hello........");
        //return AjaxResult.success(menuTreeTest());
        //RSAKeyTest();
       UserTest();
       return AjaxResult.success("OK");

    }catch (Exception err){
        err.printStackTrace();
    }
    return  AjaxResult.error("发现错误哦");
   }

   private  List<SysMenu> menuTreeTest(){
       //return menuService.getUserMenu(1);
       return null;
   }

   private void RSAKeyTest(){
       try {
           KeyPair pair= RSAUtils.generateKeyPair();
          // RSAUtils.saveKeyForEncodedBase64(pair.getPrivate(),new File("E:/private_key.txt"));
          // RSAUtils.saveKeyForEncodedBase64(pair.getPublic(),new File("E:/public_key.txt"));

           Key privateKey=pair.getPrivate();
           Key publicKey=pair.getPublic();
           byte[] encBytes = privateKey.getEncoded();
           String encBase64 = Base64.encodeBase64String(encBytes);
           byte[] encBytes1 = publicKey.getEncoded();
           String encBase641 = Base64.encodeBase64String(encBytes1);

           SysKeys sysKey=new SysKeys();
           sysKey.setId("AMS");
           sysKey.setPrivateKey(encBase64);
           sysKey.setPublicKey(encBase641);
           this.keyService.save(sysKey);
//           LoginUser user=new LoginUser();
//           user.setUsername("someone");
//           String token=this.tokenService.createToken(user);
//
//           JwtClaims claims =this.tokenService.parseToken(token);
//           System.out.println(claims.getSubject());


       } catch (Exception e) {
           throw new RuntimeException(e);
       }

   }


   private void UserTest(){
        userService.createUser("guess","guess123");
   }
}
