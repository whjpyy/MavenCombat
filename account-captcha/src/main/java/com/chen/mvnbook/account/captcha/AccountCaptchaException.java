package com.chen.mvnbook.account.captcha;

import java.io.IOException;

/**
 * Created by YouZeng on 2015-09-22.
 */
public class AccountCaptchaException extends Exception {

    public AccountCaptchaException(String s) {
        super(s);
    }

    public AccountCaptchaException(String s, IOException e) {
        super(s,e);
    }
}
