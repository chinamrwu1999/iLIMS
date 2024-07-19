package com.amswh.iLIMS.framework.security.service;

import com.amswh.iLIMS.framework.security.mapper.SysKeysMapper;
import com.amswh.iLIMS.framework.security.model.SysKeys;
import com.amswh.iLIMS.framework.utils.RSAUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysKeyService extends ServiceImpl<SysKeysMapper, SysKeys> {

    private Map<String,PrivateKey> privateKeys=new HashMap<>();
    private Map<String,PublicKey> publicKeys=new HashMap<>();

    @PostConstruct
    public void initialie(){
        try {
            List<SysKeys> allKeys = this.list();
            for (SysKeys pairKey : allKeys) {
                PublicKey   pubKey = RSAUtils.getPublicKey(pairKey.getPublicKey());
                privateKeys.put(pairKey.getId(),RSAUtils.getPrivateKey(pairKey.getPrivateKey()));
                publicKeys.put(pairKey.getId(),RSAUtils.getPublicKey(pairKey.getPublicKey()));
            }
        }catch (Exception err){
            err.printStackTrace();
        }

    }

    public PrivateKey getPrivateKey(String id){

        return this.privateKeys.get(id);
    }

    public PublicKey getPublicKey(String id){
        return this.publicKeys.get(id);
    }

    public PrivateKey getMasterPrivateKey(){
        return this.privateKeys.get("AMS");
    }

    public PublicKey getMasterPublicKey(){
        return this.publicKeys.get("AMS");
    }
}
