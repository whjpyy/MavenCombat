package com.chen.mvnbook.account.persist;

/**
 * Created by YouZeng on 2015-09-10.
 */
public interface AccountPersistService {
    Account createAccount(Account account) throws AccountPersistException;
    Account readAccount(String id) throws AccountPersistException;
    Account updateAccount(Account account) throws AccountPersistException;
    void deleteAccount(String id) throws AccountPersistException;
}
