package com.amswh.iLIMS.utils;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.PropertyDescriptor;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public  class MapUtil {

    private static PrintStream out;


    public static void copy2Map(Object sourceObject,Map<String,Object> mp) {
        Map<String, Object> map = new HashMap<>();
        BeanWrapperImpl wrapper = new BeanWrapperImpl(sourceObject);
        for (PropertyDescriptor propertyDescriptor : wrapper.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if (!propertyName.equals("class")) { // Exclude the "class" property
                Object propertyValue = wrapper.getPropertyValue(propertyName);
                if(propertyValue!=null) {
                    mp.put(propertyName, propertyValue);
                }
            }
        }
    }


    public static void copyFromMap(Map<String,Object> sourceMap, Object targetObject)  {
       Class<?> targetClass = targetObject.getClass();
       Field[] fields = targetClass.getDeclaredFields();

       for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Field field = null;
            try {
                 for(int i=0;i<fields.length;i++) {
                         if(fields[i].getName().toLowerCase().equals(key.toLowerCase())) {
                             field=fields[i];
                             break;
                         }
                 }
                 if(field!=null) {
                     field.setAccessible(true);
                     Object val= convertToFieldType(field.getType(), value);
                     if(val!=null) {
                         field.set(targetObject, val);
                     }
                 }
            } catch (Exception e) {

                continue;
            }

        }

    }
    /************************************************************/
    private static Object convertToFieldType(Class<?> fieldType, Object value) {
        Class<?> valueType=value.getClass();
        if (fieldType==valueType) return value;
        if (fieldType == int.class || fieldType == Integer.class) {

            return Integer.parseInt(value.toString());
        } else if (fieldType == double.class || fieldType == Double.class) {
            if(valueType==Double.class){
                return  value;
            }else if (valueType==Integer.class || valueType==String.class){
                return Double.parseDouble(value.toString());
            }else {
                 return ((int) value * 1.0d);
            }

        } else if(fieldType==java.time.LocalDateTime.class){
              String str=value.toString();
              if(str.indexOf("+")>2){
                  str=str.substring(0,str.indexOf('+'));
              }
              LocalDateTime localDateTime=null;
              DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");
              try {
                  localDateTime = LocalDateTime.parse(str, formatter1);
              }catch (Exception err){
                  DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                  localDateTime=LocalDateTime.parse(str,formatter2);
              }
              return  localDateTime;
        }else if(fieldType==java.time.LocalDate.class){
            String str=value.toString();
            if(str.indexOf("+")>2){
                str=str.substring(0,str.indexOf('+'));
            }


            if(str.length()==10){
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date=LocalDate.parse(str,formatter1);
                return date;
            }else{
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");
                try {
                    LocalDateTime  localDateTime= LocalDateTime.parse(str, formatter1);

                    return localDateTime.toLocalDate();
                }catch (Exception err){
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    LocalDateTime  localDateTime= LocalDateTime.parse(str,formatter2);
                    return  localDateTime.toLocalDate();
                }
            }

        }
            return value;

    }

}
