package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.User;
import com.amswh.iLIMS.domain.UserLoginStatus;
import com.amswh.iLIMS.mapper.lims.IUser;
import com.amswh.iLIMS.mapper.lims.IUserLogin;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;

import java.util.List;
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
    public UserLoginStatus queryLoginUser(String userId){

        String partyId=null;
        String password=null;
        Map<String,String> mp=userloginMapper.queryPartyByContact(userId);
        if(mp!=null && !mp.isEmpty()){
             partyId=mp.get("partyId");
             password=this.userloginMapper.getUserPassword(partyId);
             UserLoginStatus loginStatus=new UserLoginStatus(partyId,password);

            Map<String,String> deptMap=this.userloginMapper.getDepartment(partyId);
            if(deptMap!=null) { //员工
                mp.putAll(deptMap);
            }
           loginStatus.setUserInfo(mp);
             return loginStatus;
       }
      return null;
    }






}
