package com.amswh.iLIMS.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IDCardUtil {
    public static final int CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    public static final int CHINA_ID_MAX_LENGTH = 18;
    /**
     * 根据身份编号获取年龄
     *
     * @param idCard
     *            身份编号
     * @return 年龄
     */
    public static Integer getAgeByIdCard(String idCard) {
        if(idCard==null || idCard.trim().length()<CHINA_ID_MIN_LENGTH || idCard.trim().length()>CHINA_ID_MAX_LENGTH) return null;
        Integer iAge = -1;
        String birthdayString=getBirthByIdCard(idCard);
        if (birthdayString==null) return null;
        LocalDate birthday = LocalDate.of(
                Integer.parseInt(birthdayString.substring(0, 4)),  // Year
                Integer.parseInt(birthdayString.substring(4, 6)),  // Month
                Integer.parseInt(birthdayString.substring(6, 8))   // Day
        );
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthday, currentDate);
        iAge= period.getYears();

        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        if(idCard.length()==18) { //18位身份证号
            return idCard.substring(6, 14);
        }else if(idCard.length()==15){
            return "19"+idCard.substring(6, 12);
        }else{
            return null;
        }
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M-男，F-女，N-未知)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "未知";
        String sCardNum=null;
        if(idCard.trim().length()==18) {
            sCardNum = idCard.substring(16, 17);
        }else if(idCard.trim().length()==15) {
            sCardNum = idCard.substring(14, 15);
        }else {
            return null;
        }
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "M";//男
        } else {
            sGender = "F";//女
        }
        return sGender;
    }

    public static Integer getAgeByStringDate(String formattedDate){
         List<String> dateFmts=new ArrayList<>();
         dateFmts.add("yyyy-M-d");
         dateFmts.add("yyyyMMdd");

         LocalDate today=LocalDate.now();
         LocalDate birthDate=null;
         for(String fmt:dateFmts) {
             try {
                  birthDate = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern(fmt));
                  break;
             }catch (Exception err){

             }
         }
         if(birthDate!=null){
             Period period = Period.between(birthDate, today);
             return period.getYears();
         }
         return null;
    }

}
