package com.amswh.iLIMS.service.impl;


import com.amswh.iLIMS.domain.Party;
import com.amswh.iLIMS.domain.Partybar;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.PartyService;
import com.amswh.iLIMS.service.PartybarService;
import com.amswh.iLIMS.service.PersonService;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MyPartyService {


    @Resource
    PersonService personService;

    @Resource
    PartyService partyService;

    @Resource
    PartybarService partybarService;


    public Map<String,Object> fetchPatientByBarCode(Partybar pb){



        return null;
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
        this.personService.save(person);
        return person;
    }

    /*****************************************************************************************/
}

