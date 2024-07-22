package com.amswh.iLIMS.framework.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class LoginUser implements UserDetails {

    private Integer userId;
    private  String username;
    private String password;


    private Set<String> permissions;

    private Set<String> roles;

    private Map<String,Object> userInfo;

    public void setUserInfo(Map<String, Object> userInfo) {
        this.userInfo = userInfo;
    }

    public Map<String,Object> getUserInfo(){
        return  this.userInfo;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    private String cachedId;

    private long loginTime;



    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    private long expireTime;

    public LoginUser(){

    }


    public void setUserId(Integer userId){
        this.userId=userId;
    }

    public Integer getUserId(){
        return  userId;
    }

    public LoginUser(String username,String password){
        this.username=username;
        this.password=password;
    }

    public void setPermissions(Set<String> perms){
      //  System.out.println("setting permissions 000000000000000000000");
        this.permissions=perms;
    }

    public boolean hasPerms(String perm){
        if(this.permissions!=null){
            return this.permissions.contains(perm);
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.permissions!=null){
            return this.permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void pushUserInfo(String key,Object value){
        if(this.userInfo==null){
            this.userInfo=new HashMap<>();
        }
        if(key!=null && !key.isBlank()) {
            this.userInfo.put(key, value);
        }
    }

    public Object getLoginUserInfo(String key){
        if(this.userInfo!=null && key!=null && !key.isBlank()){
            return this.userInfo.get(key);
        }
        return null;
    }

    public boolean hasRole(String role){
        return this.roles.contains(role);
    }
}
