package com.bubble.common.utils;


import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhoneUtils {


    private static final Pattern PHONE_WITH_EXT
            = Pattern.compile(                      // sdd = space, dot, or dash
            "\\+(?<ext>[0-9]*)?[\\- .]"        // +<digits><sdd>*
                    + "(?<phone>[0-9]*)"); // (<digits>)<sdd>*

    private static final Pattern PHONE
            = Pattern.compile(                      // sdd = space, dot, or dash
            "(\\+[0-9]+[\\- \\.]*)?"        // +<digits><sdd>*
                    + "(\\([0-9]+\\)[\\- \\.]*)?"   // (<digits>)<sdd>*
                    + "([0-9][0-9\\- \\.]+[0-9])"); // <digit><digit|sdd>+<digit>




    public static boolean isPhoneNumber(String phone, boolean requireExt){
        return requireExt ? PHONE_WITH_EXT.matcher(phone).matches() : PHONE.matcher(phone).matches();
    }

    public static boolean isPhoneNumber(String phone){
        return isPhoneNumber(phone, true);
    }

    public static boolean isPhoneNumberWithoutExt(String phone) {
        return isPhoneNumber(phone, false);
    }

    public static Integer extractPhoneExt(String phone){
        if(phone == null || Strings.isNullOrEmpty(phone)){
            return null;
        }

        Matcher matcher = PHONE_WITH_EXT.matcher(phone);
        if(matcher.matches()) {
            String ext = matcher.group("ext");
            if (ext != null) {
                return Integer.parseInt(ext);
            }
        }

        return null;
    }

    public static String extractPhone(String phone){
        Matcher matcher = PHONE_WITH_EXT.matcher(phone);
        if(matcher.matches()){
            return matcher.group("phone");
        }

        return phone;
    }

    public static void main(String[] args){
        String phone = "85-1234567";
        System.out.println(extractPhoneExt(phone));
        System.out.println(extractPhone(phone));
    }
}
