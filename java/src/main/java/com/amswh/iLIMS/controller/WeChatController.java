package com.amswh.iLIMS.controller;

import com.amswh.framework.utils.BaseController;
import com.amswh.iLIMS.domain.PartyBar;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.PartyBarService;
import com.amswh.iLIMS.service.PartyService;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WeChatController extends BaseController {
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
    @PostMapping("/scanBarCode")
    @Transactional
    public void bindBoxByScan(@RequestBody Map<String,Object> inputMap) throws  Exception{
        if(inputMap.get("barCode")==null ) {
            throw new Exception("条码号不能缺");
        }
        PartyBar pb=new PartyBar();
        pb.setBindWay("wechat");
        MapUtil.copyFromMap(inputMap,pb);

        if(inputMap.get("partyId")==null){ //传入的不是partyId,新建Person
            Person person=this.partyService.addPerson(inputMap);
            pb.setPartyId(person.getPartyId());
        }else{
            pb.setPartyId((Integer) inputMap.get("partyId"));
        }
        partyBarService.save(pb);
    }


}
