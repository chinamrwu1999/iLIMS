package com.amswh.iLIMS.controller;

import com.amswh.framework.utils.BaseController;
import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.domain.PartyBar;
import com.amswh.iLIMS.domain.PartyRelationship;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.*;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WeChatBarController extends BaseController {
    @Resource
    PartyService partyService;

    @Resource
    BarService barService;

    @Resource
    PartyContactService contactService;

    @Resource
    PartyrelationshipService partyrelationshipService;

    @Resource
    PartyBarService partyBarService;

    @Resource
    PersonService personService;
    /*
         业务：通过微信小程序扫码绑定病人信息与采样管条码
         输入（前端校验）：
               barCode 采样管条码号（必填）
               name: 病人姓名（必填）
               gender: 性别（必填）
               age：年龄 或
               birthday：出生日期
               phone：
               IDNumber：身份证号码

    */
    @PostMapping("/scanBarCode")
    @Transactional
    public void bindBoxByScan(@RequestHeader("openId")String openId,@RequestBody Map<String,Object> inputMap) throws  Exception{
        if(inputMap.get("barCode")==null ) {
            throw new Exception("条码号不能缺");
        }
        Bar bar=barService.getBar(inputMap.get("barCode").toString());
        if(bar==null){
            throw new Exception("您扫的码不是有效的产品条码！");
        }
        PartyBar pb=new PartyBar();
        pb.setBindWay("wechat");
        MapUtil.copyFromMap(inputMap,pb);

        if(inputMap.get("partyId")==null){ //传入的不是partyId,新建Person
            Person person=this.partyService.addPerson(inputMap);
            pb.setPartyId(person.getPartyId());
        }else{
            pb.setPartyId(inputMap.get("partyId").toString());
        }
        partyBarService.save(pb);
    }

    /**
     *  手机用户在成员列表中添加新成员
     * @param inputMap
     * @throws Exception
     */
    @PostMapping("/addMember")
    @Transactional
    public void addMember(@RequestHeader("openId")String openId ,@RequestBody Map<String,Object> inputMap) throws  Exception{
        String masterId=null;
        if(inputMap.get("partyId")==null ) {
             masterId= contactService.getPartId4Wechat(openId);
        }else{
            masterId=inputMap.get("partyId").toString();
            inputMap.remove("partyId");
        }
        Person member=null;
        List<Person> existingPersons=partyService.existPerson(inputMap);
        if(existingPersons!=null && !existingPersons.isEmpty()){ //已经存在

        }else{ //不存在
            member=partyService.addPerson(inputMap);
        }
        if(member!=null) {
            PartyRelationship relationship = new PartyRelationship();
            relationship.setFromId(masterId);
            relationship.setToId(member.getPartyId());
            relationship.setRelationType("member");
            partyrelationshipService.save(relationship);
        }

    }

    /**
     * 添加新手机用户
     * @param inputMap
     * @throws Exception
     */
    @PostMapping("/newWechatUser")
    @Transactional
    public void addWeChatUser(@RequestHeader("openId")String openId ,@RequestBody Map<String,Object> inputMap) throws  Exception{
        Person person=new Person();
        MapUtil.copyFromMap(inputMap,person);

        if(inputMap.get("name")==null){
            throw new Exception("姓名不能为空");
        }
        partyService.addPerson(inputMap);

    }


}
