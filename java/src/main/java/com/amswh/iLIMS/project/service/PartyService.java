package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.*;
import com.amswh.iLIMS.project.mapper.lims.IParty;
import com.amswh.iLIMS.project.mapper.lims.IPartyGroup;

import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.utils.MapUtil;
import com.amswh.iLIMS.utils.MyStringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
    SeqService seqService;

    @Resource
    IPartyGroup partyGroupMapper;

    @Resource
    PartyContactService contactService;

    /**
     * 根据采样管条码/分析物代码获取病人信息
     * @param code : 采样管条码 或 分析物代码
     * @return
     */
    public Map<String,Object> getPatientByBarCode(String code){
        return this.personService.getBaseMapper().getPatientByBarCode(code);
        //return null;
    }

    public  String addParty(Party party){
        if(party==null ) return null;
        String partyId=party.getPartyId();
        if(partyId==null){

            party.setPartyId(seqService.nextPartySeq());
        }
        if(this.save(party)){
            partyId=party.getPartyId();

        }
        return partyId;
    }


    public boolean addPartyContact(Map<String,Object> inputMap){
        if(inputMap.get("partyId")==null) return false;
        String partyId=inputMap.get("partyId").toString();
        List<PartyContact> existedContacts=this.contactService.listContacts(partyId);//查找现有的联系方式

        String contactType="phone";
        Object obj=inputMap.get(contactType);
        if(obj!=null){
           String strContact=obj.toString().trim();

            List<PartyContact> contacts=existedContacts.stream().filter(x -> x.getContactType().equalsIgnoreCase("phone")
             && strContact.equalsIgnoreCase(x.getContact())).toList();
            if(contacts.isEmpty()){
                PartyContact contact=new PartyContact();
                contact.setPartyId(partyId);
                contact.setContactType(contactType);
                contact.setContact(obj.toString().trim());
                this.contactService.save(contact);
            }
        }

        contactType="mobile";
        obj=inputMap.get(contactType);
        if(obj==null){ obj=inputMap.get("mobilePhone");}
        if(obj!=null){
            String strContact=obj.toString().trim();
            List<PartyContact> contacts=existedContacts.stream().filter(x -> x.getContactType().equalsIgnoreCase("mobile")
                    && strContact.equalsIgnoreCase(x.getContact())).toList();
            if(contacts.isEmpty()){
                PartyContact contact=new PartyContact();
                contact.setPartyId(partyId);
                contact.setContactType(contactType);
                contact.setContact(obj.toString().trim());
                this.contactService.save(contact);
            }
        }
            contactType="wechat";
            obj=inputMap.get(contactType);
            if(obj==null){ obj=inputMap.get("openId");}
            if(obj!=null){
                String strContact=obj.toString().trim();
                List<PartyContact> contacts=existedContacts.stream().filter(x -> x.getContactType().equalsIgnoreCase("wechat")
                        && x.getContact().equalsIgnoreCase(strContact)).toList();
                if(contacts.isEmpty()){
                    PartyContact contact=new PartyContact();
                    contact.setPartyId(partyId);
                    contact.setContactType(contactType);
                    contact.setContact(obj.toString().trim());
                    this.contactService.save(contact);
                }
            }
            ////////////
            contactType="email";
            obj=inputMap.get(contactType);
            if(obj==null){ obj=inputMap.get("e-mail");}
            if(obj!=null){
                String strContact=obj.toString().trim();
                List<PartyContact> mobiles=existedContacts.stream().filter(x -> x.getContactType().equalsIgnoreCase("email")
                        && x.getContact().equalsIgnoreCase(strContact)).toList();
                if(mobiles.isEmpty()){
                    PartyContact contact=new PartyContact();
                    contact.setPartyId(partyId);
                    contact.setContactType(contactType);
                    contact.setContact(obj.toString().trim());
                    this.contactService.save(contact);
                }
            }
            return  true;
    }

    /**
     * 添加个人信息
     * @param inputMap
     * @return 返回partyId
     * @throws Exception
     */
    @Transactional("limsTransactionManager")
    public Person addPerson(Map<String,Object> inputMap) throws  Exception{

        List<Person> existeds=existPerson(inputMap); //检查是否存在该人员信息，如果有则不加
        if(existeds!=null && !existeds.isEmpty()){
            Person person=existeds.get(0);
            if(!MyStringUtils.isEmpty(person.getPartyId())) {
                inputMap.put("partyId", person.getPartyId());
                this.addPartyContact(inputMap);//添加联系人信息
                return existeds.get(0);
            }
        }
        inputMap.put("partyType","PERSON");
        Party party=new Party();
        String partyId=seqService.nextPartySeq();
        party.setPartyId(partyId);
        MapUtil.copyFromMap(inputMap, party);
        this.addParty(party);

        Person person=new Person();
        MapUtil.copyFromMap(inputMap, person);
        person.setPartyId(partyId);
        inputMap.put("partyId",partyId);
        this.addPartyContact(inputMap);
        return person;
    }


    /**
     * 添加组织机构Party
     * @param inputMap
     * @return 返回partyId
     * @throws Exception
     */
    @Transactional("limsTransactionManager")
    public PartyGroup addPartyGroup(Map<String,Object> inputMap) throws  Exception{
        inputMap.putIfAbsent("partyType", "ORG");
        Party party = new Party();
        MapUtil.copyFromMap(inputMap, party);
        String partyId=addParty(party);
        PartyGroup group=new PartyGroup();
        MapUtil.copyFromMap(inputMap, group);
        group.setPartyId(partyId);

        this.orgService.save(group);
        this.addPartyContact(inputMap);
        return group;
    }

    /**
     * 设置Party之间的联系
     * @param inputMap
     * @return 返回partyId
     * @throws Exception
     */

    public boolean setPartyRelationShip(Map<String,Object> inputMap ){
        if(inputMap.get("fromId") == null || inputMap.get("toId")==null) return false;
        PartyRelationship obj=new PartyRelationship();
        try {
            MapUtil.copyFromMap(inputMap, obj);
        }catch (Exception err){
            err.printStackTrace();
        }

        return  this.relationService.save(obj);
    }

    public int batchAddRelationships(List<PartyRelationship> objs){
       return this.baseMapper.insertPartyRelationships(objs);
    }

    public int getRootPartyId(){
        Map<String,Object> map=this.partyGroupMapper.getRootParty();
        return (Integer) map.get("partyId");
    }

    public Map<String,Object> findPartyByExternalId(String externalId){

        return baseMapper.findPartyByExternalId(externalId);
    }

    public List<Person> existPerson(Map<String,Object> inputMap){
        List<Map<String,Object>> maps=this.baseMapper.existPerson(inputMap);
        List<Person> persons=new ArrayList<>();
        for(Map<String,Object> mp:maps){
            Person person=new Person();
            try {
                MapUtil.copyFromMap(mp, person);
                persons.add(person);
            }catch (Exception err){
                err.printStackTrace();
            }

        }
        return  persons;
    }

    /**
     *  添加受检者信息
     * @param patient
     * @return
     */

    public Person savePatient(PatientInfo patient){
        if(patient==null || patient.getName()==null) return null;
        Map<String,Object> mp=new HashMap<>();
        if(patient.getName()!=null){mp.put("name",patient.getName());}
        if(patient.getGender()!=null){mp.put("gender",patient.getGender());  }
        if(patient.getAge()!=null){mp.put("age",patient.getAge());}
        if(patient.getIDNumber()!=null){mp.put("IDNumber",patient.getIDNumber());}
        if(patient.getBirthDate()!=null){mp.put("birthday",patient.getBirthDate());}
        if(patient.getIDType()!=null){mp.put("IDCardType",patient.getIDType());}
        if(patient.getPhone()!=null){mp.put("phone",patient.getPhone());}
        mp.putAll(patient.getOtherInfo());
        try {
            return this.addPerson(mp);
        }catch (Exception err){
            err.printStackTrace();
        }
        return null;

    }

    /**
     *  根据联系方式获取Party信息
     */
     public Map<String,Object> getPersonInfo(String contact){

         return baseMapper.getPersonInfByContact(contact);
     }


     public Map<String,Object> getSysUserBasicInfo(String username){
         if(username==null || username.isBlank()) return  null;
         Map<String,Object> result=new HashMap<>();
         if("admin".equalsIgnoreCase(username)){
             result.put("name","超级管理员");
             result.put("role","超级管理员");
             return  result;
         }
         result=this.findPartyByExternalId(username);
         if(!result.isEmpty()){
             return result;
         }
         return (Map<String,Object>)this.getPersonInfo(username);
     }

     public List<Map<String,Object>> listAllEmployees(){
         return baseMapper.listAllEmployee();
     }

     public List<Map<String,Object>> listDepartentEmployees(String deptId){
         return this.baseMapper.listDepartmentEmployee(deptId);
     }

}