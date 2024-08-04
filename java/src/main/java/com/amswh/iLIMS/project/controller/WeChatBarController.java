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
    PartnerBarService partnerBarService;

    @Resource
    PartyContactService contactService;

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
        String barCode=inputMap.get("barCode")!=null?inputMap.get("barCode").toString().trim():null;
        if(barCode==null || barCode.isBlank() ) {
            return AjaxResult.error("条码号不能缺失");
        }
        Bar bar=barService.getBar(barCode);
        PartnerBar partnerBar=barService.getPartnerBar(barCode);
        if(bar==null || partnerBar!=null){
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
    public AjaxResult bindBox(@RequestHeader("openId")String openId, @RequestBody Map<String,Object> input) throws  Exception{
        String barCode=input.get("barCode")!=null?input.get("barCode").toString().trim():null;
        if(barCode==null || barCode.isBlank() ) {
            return AjaxResult.error("条码号不能缺失");
        }
        Bar bar=barService.getBar(barCode);
        PartnerBar partnerBar=barService.getPartnerBar(barCode);
        if(bar==null || partnerBar!=null){
            return AjaxResult.error("您扫的码不是有效的产品条码！");
        }
        input.put("wechat",openId);
        Map<String,Object> mp=  normalService.findPartnerInfo(barCode);
        if(mp!=null){
            input.put("partnerCode",mp.get("partnerCode").toString());
        }
        if(partyBarService.saveBindedInfo(input)!=null) {
            return AjaxResult.success("绑定成功！");
        }else{
            return AjaxResult.error("绑定条码发生异常！");
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
    @PostMapping("/wechat/BarStatus")
    @Transactional
    public AjaxResult querySampleStatus(@RequestHeader("openId")String openId ,@RequestBody Map<String,Object> input) throws  Exception{
        String phone=input.get("phone")!=null?input.get("phone").toString().trim():null;
        if(phone==null){ //根据openId获取手机号
           Map<String,Object> mp=partyService.getPersonInfo(openId);
           if(mp!=null) {
               String  partyId = mp.get("partyId").toString();
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
