package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.framework.utils.SecurityUtils;
import com.amswh.framework.model.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {





       @GetMapping("/getUserMenus")
       public AjaxResult getUserMenus(){
           LoginUser loginUser= SecurityUtils.getLoginUser();
           String partyId=loginUser.getUserInformation("partyId");


           return  null;
       }
}
