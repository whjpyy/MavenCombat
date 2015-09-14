package com.chen.mvnbook.account.persist;

/**
 * Created by YouZeng on 2015-09-10.
 */
public class AccountPersistException extends Exception {

    public AccountPersistException(String s, Exception e) {
        super(s,e);
    }
}
