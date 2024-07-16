package com.amswh.framework.system.mapper;

import com.amswh.framework.system.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface IUser extends BaseMapper<User> {


    @Select("SELECT password FROM User WHERE partyId=#{partyId}")
    public String getPassword(String partyId);



}
