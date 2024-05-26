package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.Party;
import com.amswh.iLIMS.domain.PartyGroup;
import com.amswh.iLIMS.domain.PartyRelationship;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.mapper.lims.IParty;
import com.amswh.iLIMS.mapper.lims.IPartyGroup;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class PartyService extends ServiceImpl<IParty, Party> {

    @Resource
    PersonService personService;
    @Resource
    PartygroupService orgService;

    @Resource
    PartyrelationshipService relationService;

    @Resource
    PartyBarService partybarService;

    @Resource
    IPartyGroup partyGroupMapper;

    /**
     * 根据采样管条码获取病人信息
     * @param barCode
     * @return
     */
    public Map<String,Object> getPatientByBarCode(String barCode){
        return this.personService.getBaseMapper().getPatientByBarCode(barCode);
        //return null;
    }

    private  int addParty(Party party){
        if(party==null ) return -1;
        int result=-1;
        if(this.save(party)){
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
        for(String key:inputMap.keySet()){
            System.out.println(key+":"+inputMap.get(key));
        }

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
    public PartyGroup addOrganization(Map<String,Object> inputMap) throws  Exception{
        if(inputMap.get("partyType")==null){
            inputMap.put("partyType","ORG");
        }
        Party party = new Party();
        MapUtil.copyFromMap(inputMap, party);
        int partyId=addParty(party);
        PartyGroup group=new PartyGroup();
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
        PartyRelationship obj=new PartyRelationship();
        obj.setFromId((Integer) inputMap.get("fromId"));
        obj.setToId((Integer) inputMap.get("toId"));
        obj.setTypeId((Integer) inputMap.get("typeId"));
        obj.setTypeId((Integer) inputMap.get("throughDate"));
        return  this.relationService.save(obj);
    }

    public int batchAddRelationships(List<Map<String,Object>> objs){

      //  return this.relationService.batchAddPartyRelationships(objs);
        return -1;
    }

    public int getRootPartyId(){
        Map<String,Object> map=this.partyGroupMapper.getRootParty();
        return (Integer) map.get("partyId");
    }

    public Map<String,Object> findPartyByExternalId(String externalId){

        return baseMapper.findPartyByExternalId(externalId);
    }

}