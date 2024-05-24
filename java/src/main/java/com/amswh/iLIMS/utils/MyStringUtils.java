package com.amswh.iLIMS.utils;

public class MyStringUtils {

    public static boolean isEmpty(Object obj){
        if(obj==null) return true;
        return (obj.toString().trim().isEmpty());
    }

}
