package com.amswh.iLIMS.project.controller;

import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.utils.BaseController;
import com.amswh.iLIMS.partner.service.NORMALService;
import com.amswh.iLIMS.project.domain.*;
import com.amswh.iLIMS.project.service.*;

import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    NORMALService normalService;

    /*
         业务：通过微信小程序扫码进入绑定界面
         输入（前端校验）：
               barCode 采样管条码号（必填）

    */
    @PostMapping("/scanBar")
    @Transactional
    public AjaxResult bindBoxByScan(@RequestHeader("openId")String openId, @RequestBody Map<String,Object> inputMap) throws  Exception{
        if(inputMap.get("barCode")==null ) {
           // throw new Exception("条码号不能缺");
            return AjaxResult.error("条码号不能缺失");
        }
        Bar bar=barService.getBar(inputMap.get("barCode").toString());
        if(bar==null){
           return AjaxResult.error("您扫的码不是有效的产品条码！");
        }
        return AjaxResult.success("OK");
    }

    /**
     *   业务：通过微信小程序扫码绑定病人信息与采样管条码
     *          输入（前端校验）：
     *                barCode 采样管条码号（必填）
     *                name: 病人姓名（必填）
     *                gender: 性别（必填）
     *                age：年龄 或
     *                birthday：出生日期
     *                phone：
     *                IDNumber：身份证号码
     * @param openId
     * @param input
     * @return
     * @throws Exception
     */

    @PostMapping("/bindBar")
    @Transactional
    public AjaxResult bindBox(@RequestHeader("openId")String openId, @RequestBody Map<String,Object> input) throws  Exception{
        String barCode=input.get("barCode")==null?null:input.get("barCode").toString();
//        Bar bar=barService.getBar(input.get("barCode").toString());  //确保用艾米森微信小程序扫的是艾米森的条码
//        PartyBar existed=partyBarService.getBarByCode(barCode);
//        if(bar==null || existed!=null){
//            return AjaxResult.error("您扫的码是无效条码！");
//        }

        PartyBar pb=new PartyBar();
        pb.setBindWay("wechat");
        MapUtil.copyFromMap(input,pb);
       if(input.get("partyId")==null){ //传入的不是partyId,新建Person
            input.put("wechat",openId);
            input.put("partyType","PATIENT");
            Person person=this.partyService.addPerson(input);
           Map<String,Object> partnerMap=  normalService.findPartnerInfo(barCode);
           if(partnerMap!=null){
               pb.setPartnerCode(partnerMap.get("partnerCode").toString());
           }else{
               pb.setPartnerCode("unknown");
           }
            pb.setPartyId(person.getPartyId());
        }
//       else{
//            pb.setPartyId(input.get("partyId").toString());
//        }
        partyBarService.save(pb);
        return AjaxResult.success("绑定成功！");
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
     * 微信新用户授权注册并登录
     * @param inputMap
     * @throws Exception
     */
    @PostMapping("/register")
    @Transactional
    public void addWeChatUser(@RequestHeader("openId")String openId ,@RequestBody Map<String,Object> inputMap) throws  Exception{
        Person person=new Person();
        inputMap.put("wechat",openId);
        MapUtil.copyFromMap(inputMap,person);

        if(inputMap.get("name")==null){
            throw new Exception("姓名不能为空");
        }
        partyService.addPerson(inputMap);

    }

    /** 根据手机号，查看关联的样本检测进度
     *
     */
    @PostMapping("/querySampleStatus")
    @Transactional
    public AjaxResult querySampleStatus(@RequestHeader("openId")String openId ,@RequestBody Map<String,Object> inputMap) throws  Exception{
        String phone=null;
        String partyId=null;
       if(inputMap!=null && ! inputMap.isEmpty() && inputMap.get("phone") !=null){
           phone=inputMap.get("phone").toString();
       }
       if(phone==null){ //根据openId获取手机号
           Map<String,Object> mp=partyService.getPersonInfo(openId);
           if(mp!=null) {
               partyId = mp.get("partyId").toString();
               phone=contactService.getPartyContact(partyId,"mobile");
           }
       }
       if(phone!=null ){
           List<String> partyIds=this.contactService.listPartiesWithSameContact(phone);
           List<Map<String,Object>> analytes=this.partyBarService.getBarStatus(partyIds);
           return AjaxResult.success(analytes);
        }else{
           return AjaxResult.error("未找到该手机关联的信息");
       }
     }


}
