package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.framework.security.SecurityUtils;
import com.amswh.iLIMS.domain.UserLoginStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserLoginController {





       @GetMapping("/getUserMenus")
       public AjaxResult getUserMenus(){
           UserLoginStatus loginUser= SecurityUtils.getLoginUser();
           String partyId=loginUser.getUserInformation("partyId");


           return  null;
       }
}
