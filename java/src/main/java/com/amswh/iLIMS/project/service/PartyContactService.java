package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.PartyContact;
import com.amswh.iLIMS.project.mapper.lims.IPartyContact;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyContactService extends ServiceImpl<IPartyContact, PartyContact> {

    public String getPartId4Wechat(String openId){
         if(openId==null || openId.trim().length()<5) return null;
         return this.baseMapper.getPartyIdOfWheChat(openId);
    }

    public List<PartyContact> listContacts(String partyId){
        return this.baseMapper.listContacts(partyId);
    }


    public String getPartyContact(String partyId,String contactType){
          return this.baseMapper.getContact(partyId,contactType);
    }


    public List<String> listPartiesWithSameContact(String contact){
         return this.baseMapper.listPartiesWithSameContact(contact);
    }
}
