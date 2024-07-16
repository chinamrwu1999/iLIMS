package com.amswh.framework.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class LoginUser implements UserDetails {

    private Long userId;
    private  String username;
    private String password;


    private Set<String> perms;

    public LoginUser(){

    }


    public void setUserId(Long userId){
        this.userId=userId;
    }

    public Long getUserId(){
        return  userId;
    }

    public LoginUser(String username,String password){
        this.username=username;
        this.password=password;
    }

    public void setPermissions(Set<String> perms){
        System.out.println("setting permissions 000000000000000000000");
        this.perms=perms;
    }

    public boolean hasPerms(String perm){
        if(this.perms!=null){
            return this.perms.contains(perm);
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.perms!=null){
            return this.perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
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
}
