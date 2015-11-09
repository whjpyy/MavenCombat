package com.chen.mvnbook.account.service;
/**
 * Created by YouZeng on 2015-09-30.
 */
public interface AccountService {

    String generateCaptchaKey() throws AccountServiceException;
    byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException;
    void signUp(SignUpRequest signUpRequest) throws AccountServiceException;
    void activate(String activationNumber) throws AccountServiceException;
    void login(String id, String password) throws AccountServiceException;

}
