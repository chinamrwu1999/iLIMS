package com.amswh.iLIMS.partner;

import java.util.Map;

/**
 *   本接口定义所有用API接口交换数据的必需实现的方法
 */
public interface IPartner {

    /**
     * 根据条码从Partner处获取病人的信息
     *
     * @param barCode
     * @return
     */
    public Map<String,Object> fetchPatientInfo(String barCode) throws Exception;

    public String whoAmI();

}
