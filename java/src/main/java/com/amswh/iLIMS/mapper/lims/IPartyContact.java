package com.amswh.iLIMS.mapper.lims;

import com.amswh.iLIMS.domain.PartyContact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPartyContact extends BaseMapper<PartyContact> {

    @Select("SELECT partyId FROM partyContact WHERE contactType='wechat' and contact=#{contact}")
    public String getPartyIdOfWheChat(String openId);

    @Select("SELECT * FROM PartyContact WHERE partyId=#{partyId}")
    public List<PartyContact> listContacts(String partyId);
}
