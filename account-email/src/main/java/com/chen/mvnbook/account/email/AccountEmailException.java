package com.chen.mvnbook.account.email;

import javax.mail.MessagingException;

/**
 * Created by YouZeng on 2015-09-07.
 */
public class AccountEmailException extends Exception{

    public AccountEmailException(String s, MessagingException e) {
        super(s);
    }
}
