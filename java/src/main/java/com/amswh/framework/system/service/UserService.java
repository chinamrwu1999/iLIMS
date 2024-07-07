package com.amswh.framework.system.service;

import com.amswh.framework.system.model.User;
import com.amswh.framework.system.model.LoginUser;
import com.amswh.framework.system.mapper.IUser;
import com.amswh.framework.system.mapper.IUserLogin;
import com.amswh.iLIMS.service.PartyContactService;
import com.amswh.iLIMS.service.PartyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;

import java.util.Map;

public class UserService extends ServiceImpl<IUser, User> {

    @Resource
    IUserLogin  userloginMapper;

    @Resource
    PartyService partyService;

    @Resource
    PartyContactService contactService;


    /**
     * 登录系统
     * @param userId： 工号或手机号或openId
     * @return
     */
    public LoginUser queryLoginUser(String userId){

        String partyId=null;
        String password=null;
        Map<String,String> mp=userloginMapper.queryPartyByContactOrEmployeeId(userId);
        if(mp!=null && !mp.isEmpty()){
             partyId=mp.get("partyId");
             password=this.userloginMapper.getUserPassword(partyId);
             LoginUser loginUser=new LoginUser(partyId,password);
             Map<String,String> deptMap=this.userloginMapper.getDepartment(partyId);
             if(deptMap!=null) { //员工
                mp.putAll(deptMap);
             }
            loginUser.setUserInfo(mp);
             return loginUser;
       }
      return null;
    }






}
