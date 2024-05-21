package com.amswh.iLIMS.controller;
/*

 */



import com.amswh.iLIMS.domain.PartyBar;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.PartyBarService;
import com.amswh.iLIMS.service.PartyService;
import com.amswh.iLIMS.service.PersonService;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Resource
    PartyService partyService;

    @Resource
    PartyBarService partyBarService;


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
    @PostMapping("/bindBox")
    @Transactional
    public void bindBoxByScan(@RequestBody Map<String,Object> inputMap) throws  Exception{
        if(inputMap.get("barCode")==null ) {
            throw new Exception("条码号和绑定对象不能缺");
        }
        PartyBar pb=new PartyBar();
        pb.setBindWay("wechat");
        MapUtil.copyFromMap(inputMap,pb);

        if(inputMap.get("partyId")==null){
            Person person=this.partyService.addPerson(inputMap);
            pb.setPartyId(person.getPartyId());
        }

        Map<String,Object> mp=partyBarService.getBaseMapper().findPartner(pb.getBarCode());
        Integer partnerId=null;
        String productNo=null;
        if( !(mp==null || mp.isEmpty())){
            partnerId=(Integer)mp.get("partyId");
        }else{ // 销售发货记录中未找到条码的客户信息,表面条码不是出自艾米森，而是partner自己的条码。需要轮询调用各partner的api来朔源

        }




    }





}
