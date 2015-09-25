package com.chen.mvnbook.account.captcha;

import java.util.Random;

/**
 * Created by YouZeng on 2015-09-22.
 */
public class RandomGenerator {

    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static synchronized String getRandomString(){
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for(int i = 0;i < 8;i ++){
            result.append(range.charAt(random.nextInt(range.length())));
        }
        return result.toString();
    }
}
