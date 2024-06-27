package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.User;
import com.amswh.iLIMS.mapper.lims.IUser;
import com.amswh.iLIMS.mapper.lims.IUserLogin;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;

public class UserService extends ServiceImpl<IUser, User> {

    @Resource
    IUserLogin  userloginMapper;

    @Resource
    PartyService partyService;

    @Resource
    PartyContactService contactService;


    /**
     * 登录LIS系统
     * @param userId： 工号或手机号
     * @param password：密码
     * @return
     */
    public boolean LoginLIS(String userId,String password){

        

        return null;
    }




}
