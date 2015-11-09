package com.chen.mvnbook.account.service;

import com.chen.mvnbook.account.captcha.AccountCaptchaException;

/**
 * Created by YouZeng on 2015-09-30.
 */
public class AccountServiceException extends Exception {

    public AccountServiceException(String s, AccountCaptchaException e) {
        super(s, e);
    }

    public AccountServiceException(String s) {
        super(s);
    }
}
