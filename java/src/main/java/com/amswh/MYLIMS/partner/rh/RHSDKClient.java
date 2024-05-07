package com.amswh.MYLIMS.partner.rh;

import com.iss.iescp.web.sign.sdk.bas.SignClient;
import com.iss.iescp.web.sign.sdk.bas.SignClientConstant;
import com.iss.iescp.web.sign.sdk.bas.SignMethodEnum;
import com.iss.iescp.web.sign.sdk.bas.SignServerException;
import com.iss.iescp.web.sign.sdk.json.JSONObject;

import java.util.TreeMap;

/**
 *    瑞华SDK的PcisServiceClient类的包装，实际没使用，学习用
 */

public class RHSDKClient {
    /** 验签服务路径 */
    private static final String CHECK_SIGN_PATH="/checksign/v1";

    protected SignClient client;
    public RHSDKClient(String endpoint , String secretId, String secretKey)
    {
        this.client = new SignClient(endpoint, CHECK_SIGN_PATH, secretId, secretKey, SignClientConstant.REQ_METHOD_POST);
    }

    public RHSDKClient(String secretId, String secretKey,String endpoint, String path, String method)
    {
        this.client = new SignClient(endpoint, path, secretId, secretKey, method);
    }

    /**
     * setSignMethod set the sign meth and now we suppport sha1 and sha256
     **/

    public void setSignMethod(SignMethodEnum signMethod)
    {
        this.client.setSignMethod(signMethod);
    }




    //**************************************************************************
    /**
     * 调用服务通用方法<br>
     * 方法callService详细说明
     * @param String serviceName 服务名，格式：服务类名.服务方法名
     * @param String serviceParam 服务参数，JSON格式字符串
     * @return String 返回值 返回JSON格式字符串
     */
    //**************************************************************************
    public String callService(String serviceName,String serviceParam) throws Exception
    {
        TreeMap<String, String> param = new TreeMap<String, String>();
        param.put(SignClientConstant.PARAM_Z_DATA,serviceParam);
        String result = this.client.call(serviceName, param);
        if(result!=null && result.indexOf(SignClientConstant.PARAM_SIGN_CODE)>0 && result.indexOf(SignClientConstant.PARAM_SIGN_MSG)>0) {
            JSONObject jsonObj = new JSONObject(result);
            String signStatus = jsonObj.getString(SignClientConstant.PARAM_SIGN_CODE);
            String signMsg=jsonObj.getString(SignClientConstant.PARAM_SIGN_MSG);
            String reqId=jsonObj.getString(SignClientConstant.PARAM_REQ_ID);
            throw new SignServerException(signStatus,signMsg,reqId);
        }
        return result;
    }

}
