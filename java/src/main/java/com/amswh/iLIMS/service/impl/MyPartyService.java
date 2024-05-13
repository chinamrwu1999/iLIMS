package com.amswh.iLIMS.service.impl;


import com.amswh.iLIMS.domain.*;
import com.amswh.iLIMS.service.*;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MyPartyService {


    @Resource
    PersonService personService;

    @Resource
    PartygroupService orgService;


    @Resource
    PartyrelationshipService relationService;

    @Resource
    PartyService partyService;

    @Resource
    PartybarService partybarService;


    /**
     * 根据采样管条码获取病人信息
     * @param barCode
     * @return
     */
    public Map<String,Object>  getPatientByBarCode(String barCode){
        return this.personService.getBaseMapper().getPatientByBarCode(barCode);
        //return null;
    }

    private  int addParty(Party party){
        if(party==null ) return -1;
        int result=-1;
        if(this.partyService.save(party)){
            result=party.getPartyId();
            System.out.println(result);
        }
        return result;
    }

    /**
     * 添加个人信息
     * @param inputMap
     * @return 返回partyId
     * @throws Exception
     */
    @Transactional("limsTransactionManager")
    public Person addPerson(Map<String,Object> inputMap) throws  Exception{
        inputMap.put("partyType","PSON");
        Party party = new Party();
        MapUtil.copyFromMap(inputMap, party);

        int partyId=addParty(party);
        Person person=new Person();
        MapUtil.copyFromMap(inputMap, person);
        person.setPartyId(partyId);
        System.out.println("partyId:"+person.getPartyId()+" name:"+person.getName()+" height is "+person.getHeight());
        this.personService.save(person);
        return person;
    }


    /**
     * 添加组织机构Party
     * @param inputMap
     * @return 返回partyId
     * @throws Exception
     */
    @Transactional("limsTransactionManager")
    public Partygroup addOrganization(Map<String,Object> inputMap) throws  Exception{
        if(inputMap.get("partyType")==null){
            inputMap.put("partyType","ORG");
        }
        Party party = new Party();
        MapUtil.copyFromMap(inputMap, party);
        int partyId=addParty(party);
        Partygroup group=new Partygroup();
        MapUtil.copyFromMap(inputMap, group);
        group.setPartyId(partyId);

        this.orgService.save(group);
        return group;
    }

    /**
     * 设置Party之间的联系
     * @param inputMap
     * @return 返回partyId
     * @throws Exception
     */

    public boolean setPartyRelationShip(Map<String,Object> inputMap ){
        Partyrelationship obj=new Partyrelationship();
        obj.setFromId((Integer) inputMap.get("fromId"));
        obj.setToId((Integer) inputMap.get("toId"));
        obj.setTypeId((Integer) inputMap.get("typeId"));
        obj.setTypeId((Integer) inputMap.get("throughDate"));
        return  this.relationService.save(obj);
    }

    public int batchAddRelationships(List<Map<String,Object>> objs){
         return this.relationService.batchAddPartyRelationships(objs);
    }


}

