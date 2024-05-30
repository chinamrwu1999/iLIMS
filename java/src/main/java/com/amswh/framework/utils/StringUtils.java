package com.amswh.framework.utils;

import com.amswh.framework.Constants;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.AntPathMatcher;

import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtils {

    /** 空字符串 */
    private static final String NULLSTR = "";

    /** 下划线 */
    private static final char SEPARATOR = '_';

    private static final String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtmnGPqPcV/7KGTbPOpddxyOhMTsDxHzlQga3gUFgzIsN6h/3HKEVAcODTokByQ7QXbM6QwdFAOJ+nyT/qM+GbkBmxeVat57Rk4Gwnen1vrJA85g1IBie+2N7kAhwzsQyixsdrhmJDaH+n2NV6sakU4IneJtmd/MN7/3XmlN9oOgCtSYdnfYuHA63frgRDOrotb31PIyV9CdK3pqI0vekWLu59uNGgvvGM5PvkTrDzm9nMUmU+BKvVHgb1oN+hShR73mw4XWbiRnHW8Dtkt74LkNPlcyCp0NKKwdRcvZq6TgWex2PYVITke4URpF1UMmrkRzvIg3AbP8c7pzTXd/MTQIDAQAB";

    private static final String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC2acY+o9xX/soZNs86l13HI6ExOwPEfOVCBreBQWDMiw3qH/ccoRUBw4NOiQHJDtBdszpDB0UA4n6fJP+oz4ZuQGbF5Vq3ntGTgbCd6fW+skDzmDUgGJ77Y3uQCHDOxDKLGx2uGYkNof6fY1XqxqRTgid4m2Z38w3v/deaU32g6AK1Jh2d9i4cDrd+uBEM6ui1vfU8jJX0J0remojS96RYu7n240aC+8Yzk++ROsPOb2cxSZT4Eq9UeBvWg36FKFHvebDhdZuJGcdbwO2S3vguQ0+VzIKnQ0orB1Fy9mrpOBZ7HY9hUhOR7hRGkXVQyauRHO8iDcBs/xzunNNd38xNAgMBAAECggEATG2nHWnEka30eXoIe7EeHqjkCd+DJZl66R4tVt/QsgL1MopwHDO1vIZxhr9K2zX0wb2thJYQKF291epHoDBLT/h11ybQyqylfNtS5+c2Cs9e/XFhmPAX9JZrGB8hPNDhqH0VA3Q5g1fDen7BadwOz9pxtUvPNCTHSy2dX1DqCzdVSNY4n5V53v+Iv8fk2uC0xE7qIZG0ObkaYo2VkflLClBJw8XLGyyS1i1AaHOrUatT+ypWWBg/DxpXKRX9S9ukOx4eV2WE9luhoT73y3xXy2ulUomaPchSjYmTp5T9TSzIZaD+7/TszZ6RLze54SHhx1l3WPBXm1kIqkukKXWPtQKBgQDkKdbQMtp43hoHdEPOGqk06vKBS79Vmcalm1Gpsmo+QJJpHjSF6JQiywfcfcPBFvn3VUvQavzw3+aNO8E6p8PZSau3rhKhJtXpMRlHsFF4Sdlug0MmoLPKPuoadA8ZpghtYYh1G8SkHOX1rBMHkzJMkyiqj+NpTXMqJ/xWtuvNBwKBgQDMqwf8j9lmDFEBmtCNncQ32il4ncNvqcmmPrUVF75OnQn2PFcW40H2yUW6IzEqNwmvYbB3UyMVb4MFIg/gHDh+cILYMUpOW29loX1NdiTKW2+kqGBZhgKIb34UM2n68HjI5ZWekx789zuv1Gi7sQHqP1MfbwepvLUqSCrAfp/bCwKBgEURgUyvSEF0go6lHG2E1poFUXngc0A3d9HkDbmAH/dOVsPpd97dhBJwQaX2kE6gZPUBZhzmdkF4lfNFFn5qELhzO78zu8H63qBj05JHBrKSMHbq12YXGbXsi7OMVJHgGAb8knFZAZLoiJhxCaWuYniZx6KyQhY7ctRdHF8nqoP7AoGANxXlXWW+Jog0GVF4xlTrJfky5kLYAirZmkQQau/sCa6cqsiMzB04eNqtSK8GIY2Urr6FR2h57eAIjI4hZblSvz07CkMh87sKXXk8h2e/+TLo9pFW9WFrNxuC/J7l7GNirveT8I06bu2ABBAQbEv07v5COhihIF8pB4tK7mpmTgcCgYEAhxJCZWNtqc5jY2QQgsdTXJgNgQNV8LdEdeqlWKhe5rz4OQzp3y3vnhLzUvchGZbboZwYaVJptaPHoIWbq/ev4XZdcaPEjDJNGUBYXW9vlkvhdRS6lnOTkAMIFOsqAqe8+Mrh8rLAaUNMNbp9yvd4nD0psl4h9Xqk/ITRRHrZggo=";

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     ** @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str 字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str 字符串
     * @param start 开始
     * @param end 结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }


    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link)
    {
      //  return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
        return link!=null && (link.toLowerCase().startsWith("http") || link.toLowerCase().startsWith("https"));
    }

    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static final Set<String> str2Set(String str, String sep)
    {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * 字符串转list
     *
     * @param str 字符串
     * @param sep 分隔符
     * @param filterBlank 过滤纯空白
     * @param trim 去掉首尾空白
     * @return list集合
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim)
    {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str))
        {
            return list;
        }

        // 过滤空白字符串
        if (filterBlank && (str==null || str.trim().isEmpty()))
        {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split)
        {
            if (filterBlank && (string==null || string.trim().isEmpty()))
            {
                continue;
            }
            if (trim)
            {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串同时串忽略大小写
     *
     * @param cs 指定字符串
     * @param searchCharSequences 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     */
//    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences)
//    {
//        if (isEmpty(cs) || isEmpty(searchCharSequences))
//        {
//            return false;
//        }
//        for (CharSequence testStr : searchCharSequences)
//        {
//            if (containsIgnoreCase(cs, testStr))
//            {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty())
        {
            // 没必要转换
            return "";
        }
        else if (!name.contains("_"))
        {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels)
        {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty())
            {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == SEPARATOR)
            {
                upperCase = true;
            }
            else if (upperCase)
            {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str 指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs)
    {
        if (isEmpty(str) || isEmpty(strs))
        {
            return false;
        }
        for (String pattern : strs)
        {
            if (isMatch(pattern, str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url 需要匹配的url
     * @return
     */
    public static boolean isMatch(String pattern, String url)
    {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }

    public static String unRepeatSixCode() {
        String sixChar = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String time = sdf.format(date);
        for (int i = 0; i < time.length() / 2; i++) {
            String singleChar;
            String x = time.substring(i * 2, (i + 1) * 2);
            int b = Integer.parseInt(x);
            if (b < 10) {
                singleChar = Integer.toHexString(Integer.parseInt(x));
            } else if (b >= 10 && b < 36) {
                singleChar = String.valueOf((char) (Integer.parseInt(x) + 55));
            } else {
                singleChar = String.valueOf((char) (Integer.parseInt(x) + 61));
            }
            sixChar = sixChar + singleChar;

        }
        return sixChar;
    }


    /**
     * 手机号码前三后四脱敏
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return mobile;
        }
        byte[] signByte = new byte[0];
        try {
            signByte = RSAUtils.clientEncrypt(mobile.getBytes(),
                    pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org.apache.commons.codec.binary.Base64.encodeBase64String(signByte);
    }

    /**
     * 手机号码前三后四脱敏
     */
    public static String mobileDecrypt(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return mobile;
        }
        byte[] signByte = new byte[0];
        try {
            signByte =  RSAUtils.serverDecrypt(org.apache.commons.codec.binary.Base64.decodeBase64(mobile),
                    priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(signByte);
    }

    /*
     * 身份证前三后四脱敏
     */
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        byte[] signByte = new byte[0];
        try {
            signByte = RSAUtils.clientEncrypt(id.getBytes(),
                    pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org.apache.commons.codec.binary.Base64.encodeBase64String(signByte);
    }

    /*
     * 身份证前三后四解密
     */
    public static String idDecrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        byte[] signByte = new byte[0];
        try {
            signByte = RSAUtils.serverDecrypt(Base64.decodeBase64(id),
                    priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(signByte);
    }

    /*
     * 护照前2后3位脱敏，护照一般为8或9位
     */
    public static String idPassport(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*") + id.substring(id.length() - 3);
    }
}
