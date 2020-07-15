package com.mushr00m.utils.mushr00mUtils;

public class StringFilter {
    //将输入的字符串删除指定的片段，然后返回修改后的字符串
    public static String removeAppointString(String old,String appoint){
        String result = old.replaceAll(appoint,"");
        return result;
    }

}
