package com.example.codegenerator.utils;

/**
 * 字符串处理工具类
 * @author loquy
 */
public class StrUtils {
    /**
     * 去掉下划线转驼峰  user_name  -> userName
     */
    public static String changeColumnStr(String str) {
        String name = str;
        String underline = "_";
        if (name.indexOf(underline) > 0 && name.length() != name.indexOf(underline) + 1) {
            int lengthPlace = name.indexOf(underline);
            name = name.replaceFirst(underline, "");
            String s = name.substring(lengthPlace, lengthPlace + 1);
            s = s.toUpperCase();
            str = name.substring(0, lengthPlace) + s + name.substring(lengthPlace + 1);
        } else {
            return str;
        }
        return changeColumnStr(str);
    }

    /**
     * 去掉下划线转驼峰  tb_user  -> TbUser
     */
    public static String changeTableStr(String str) {
        String s = changeColumnStr(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
