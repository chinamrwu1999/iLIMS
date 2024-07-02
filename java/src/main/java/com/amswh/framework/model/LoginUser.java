package com.amswh.framework.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Data
public class LoginUser implements UserDetails
{
      private static final long serialVersionUID = 1L;

      private String Token;
      private String username;
      private String password;
      private Long loginTime;
      private Long expireTime;
      private Set<String> permissions;
      private Map<String,String> userInfo;


      public LoginUser(String partyId, String password){
           this.username =partyId;
           this.password=password;
      }


      public void addUserInformation(String key,String value){
            this.userInfo.putIfAbsent(key,value);
      }

      public String getUserInformation(String key){
            return this.userInfo.get(key);
      }

      public void printUserInformation(){
            for(String key:userInfo.keySet()){
                  System.out.println(key+":"+userInfo.get(key));
            }
      }


      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
      }

      @Override
      public String getPassword() {
            return password;
      }

      @Override
      public String getUsername() {
            return this.username;
      }

      @Override
      public boolean isAccountNonExpired() {
            return false;
      }

      @Override
      public boolean isAccountNonLocked() {
            return false;
      }

      @Override
      public boolean isCredentialsNonExpired() {
            return false;
      }

      @Override
      public boolean isEnabled() {
            return true;
      }
}
