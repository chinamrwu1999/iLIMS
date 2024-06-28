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
     * 员工登录LIS系统
     * @param userId： 工号或手机号
     * @param password：密码
     * @return
     */
    public UserLoginStatus Login(String userId, String password){

        Map<String,String> mp=userloginMapper.LoginLIS(userId);
        if(mp!=null && !mp.isEmpty()){ // 员工身份确认
            String partyId=mp.get("partyId");
            if(this.matchPassword(partyId,password)) {// 核验密码正确
                 Map<String,String> deptMap=this.getDepartment(partyId);
                 mp.putAll(deptMap);
                 mp.put("userType","LIS");
                 UserLoginStatus loginStatus=new UserLoginStatus();
                 loginStatus.setCode(200);
                 loginStatus.message="登录成功";
                 loginStatus.setUserInfo(mp);
                 return loginStatus;
            }else{ // 核验密码错误
                UserLoginStatus loginStatus=new UserLoginStatus();
                loginStatus.setCode(400);
                loginStatus.message="密码错误";
                return loginStatus;
            }
       }else{ //非员工身份登录（例如受检者）
             Map<String,String>  partyMap= this.userloginMapper.LoginByWechat(userId);
             if(partyMap!=null){
                 Map<String,String> personInf= partyService.getPersonInfo(userId);

             }
        }



        return null;
    }


    public Map<String,String> getDepartment(String partyId){
          return this.userloginMapper.getDepartment(partyId);
    }

    public boolean matchPassword(String partyId,String password){
          Map<String,String> user=this.userloginMapper.matchPassword(partyId,password);
          return user!=null && user.get("partyId")!=null;
    }


}
