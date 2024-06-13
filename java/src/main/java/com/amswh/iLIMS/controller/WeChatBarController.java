package com.amswh.iLIMS.controller;

import com.amswh.framework.model.AjaxResult;
import com.amswh.framework.utils.BaseController;
import com.amswh.iLIMS.domain.*;
import com.amswh.iLIMS.service.*;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Resource
    PartnerBarService partnerBarService;

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
     * @param inputMap
     * @return
     * @throws Exception
     */

    @PostMapping("/bindBar")
    @Transactional
    public AjaxResult bindBox(@RequestHeader("openId")String openId, @RequestBody Map<String,Object> inputMap) throws  Exception{
        Bar bar=barService.getBar(inputMap.get("barCode").toString());  //确保用艾米森微信小程序扫的是艾米森的条码
        if(bar==null){
            return AjaxResult.error("您扫的码不是有效的产品条码！");
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
        PartnerBar partnerBar=new PartnerBar();
        partnerBar.setBarCode(bar.getBarCode());
        partnerBar.setPartnerId("root");
        partnerBar.setProductCode(bar.getProductCode());
        partnerBarService.save(partnerBar);
        return AjaxResult.success("绑定成功");
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

    /** 根据手机号，查看关联的样本检测进度
     *
     */
    @PostMapping("/querySampleStatus")
    @Transactional
    public AjaxResult querySampleStatus(@RequestHeader("openId")String openId ,@RequestBody Map<String,Object> inputMap) throws  Exception{
        String phone=null;
        List<String> partyIds=null;
       if(inputMap!=null && ! inputMap.isEmpty() && inputMap.get("phone") !=null){
           phone=inputMap.get("phone").toString();
       }
       if(phone==null){ //根据openId获取手机号
           List<Map<String,Object>> mp=partyService.getPersonInfo("wechat",openId);
           partyIds=mp.stream().map(x -> x.get("partyId").toString()).toList();
       }
       if(partyIds!=null && ! partyIds.isEmpty()){
           List<Map<String,Object>> analytes=this.partyBarService.getBarStatus(partyIds);
           return AjaxResult.success(analytes);
        }else{
           return AjaxResult.error("未找到该手机关联的信息");
       }
     }


}
