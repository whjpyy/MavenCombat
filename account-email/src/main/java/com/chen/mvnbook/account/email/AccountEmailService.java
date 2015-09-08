package com.chen.mvnbook.account.email;

/**
 * Created by YouZeng on 2015-09-07.
 */
public interface AccountEmailService {
    void sendMail(String to, String subject, String htmlText) throws AccountEmailException;
}
