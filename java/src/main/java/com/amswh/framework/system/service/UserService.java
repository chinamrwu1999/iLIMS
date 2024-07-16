package com.amswh.framework.system.service;

import com.amswh.framework.system.model.LoginLog;
import com.amswh.framework.system.model.User;
import com.amswh.framework.system.mapper.IUser;
import com.amswh.framework.system.mapper.IUserLogin;
import com.amswh.framework.utils.SecurityUtils;
import com.amswh.iLIMS.service.PartyContactService;
import com.amswh.iLIMS.service.PartyService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
             mp.put("loginId",userId);
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


    public int writeLoginLog(LoginLog log){
         return this.userloginMapper.insert(log);
    }

    /**
     * 为Party 创建密码
     * @param partyId
     * @param password
     * @return
     */
    public boolean createUser(String partyId,String password){
         String encodedPwd= SecurityUtils.encryptPassword(password);
         User user=new User();
         user.setPartyId(partyId);
         user.setPassword(encodedPwd);
         return this.baseMapper.insert(user) >0;
    }

    public boolean updatePassword(String partyId,String oldPassword,String newPassowd){
                 if(SecurityUtils.matchesPassword(oldPassword,this.getEncodedPassword(partyId))){
                      User user=new User();
                      user.setPartyId(partyId);
                      user.setPassword(SecurityUtils.encryptPassword(newPassowd));
                      return this.baseMapper.update(new UpdateWrapper<User>(user)) > 0;
                 }
                 return  false;
    }

    public String getEncodedPassword(String partyId){
             return  this.baseMapper.getPassword(partyId);
    }








}
