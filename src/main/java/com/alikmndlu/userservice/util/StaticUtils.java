package com.alikmndlu.userservice.util;

import org.springframework.util.DigestUtils;

public class StaticUtils {

    // Md5 Encoder
    public static String md5Encrypt(String string){
        return DigestUtils.md5DigestAsHex(string.getBytes());
    }
}
