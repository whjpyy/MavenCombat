package com.chen.mvnbook.account.service;

import com.chen.mvnbook.account.captcha.AccountCaptchaException;
import com.chen.mvnbook.account.captcha.AccountCaptchaService;
import com.chen.mvnbook.account.captcha.RandomGenerator;
import com.chen.mvnbook.account.email.AccountEmailException;
import com.chen.mvnbook.account.email.AccountEmailService;
import com.chen.mvnbook.account.persist.Account;
import com.chen.mvnbook.account.persist.AccountPersistException;
import com.chen.mvnbook.account.persist.AccountPersistService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YouZeng on 2015-09-30.
 */
public class AccountServiceImpl implements AccountService {

    private AccountPersistService accountPersistService;
    private AccountEmailService accountEmailService;
    private AccountCaptchaService accountCaptchaService;

    private Map<String ,String> activationMap = new HashMap<String, String>();

    @Override
    public String generateCaptchaKey() throws AccountServiceException {
        try{
            return accountCaptchaService.generateCaptchaKey();
        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to generate Captcha Key.", e);
        }
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException {
        try {
            return accountCaptchaService.generateCaptchaImage(captchaKey);
        } catch (AccountCaptchaException e) {
            throw new AccountServiceException("Unable to generate Captcha Image", e);
        }
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) throws AccountServiceException {
        try{
            if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())){
                throw new AccountServiceException("2 passwords do not match.");
            }
            if(!accountCaptchaService.validateCaptcha(signUpRequest.getCaptchaKey(), signUpRequest.getCaptchaValue())){
                throw new AccountServiceException("Incorrect Captcha");
            }
            Account account = new Account();
            account.setId(signUpRequest.getId());
            account.setEmail(signUpRequest.getEmail());
            account.setName(signUpRequest.getName());
            account.setPassword(signUpRequest.getPassword());
            account.setActivated(false);

            accountPersistService.createAccount(account);
            String activationId = RandomGenerator.getRandomString();
            activationMap.put(activationId, account.getId());
            String link = signUpRequest.getActivateServiceUrl().endsWith("/") ?
                    signUpRequest.getActivateServiceUrl() + activationId :
                    signUpRequest.getActivateServiceUrl() + "?key=" + activationId;
            accountEmailService.sendMail(account.getEmail(), "Please Activate Your Account", link);
        } catch (AccountCaptchaException e) {
            e.printStackTrace();
        } catch (AccountPersistException e) {
            e.printStackTrace();
        } catch (AccountEmailException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate(String activationNumber) throws AccountServiceException {
        String accountId = activationMap.get(activationNumber);
        if(accountId == null){
            throw new AccountServiceException("Invalid account activation ID.");
        }
        try{
            Account account = accountPersistService.readAccount(accountId);
            account.setActivated(true);
            accountPersistService.updateAccount(account);
        } catch (AccountPersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String id, String password) throws AccountServiceException {
        try{
            Account account = accountPersistService.readAccount(id);
            if(account == null){
                throw new AccountServiceException("account does not exists.");
            }
            if(!account.isActivated()){
                throw new AccountServiceException("account is not disabled.");
            }
            if(!account.getPassword().equals(password)){
                throw new AccountServiceException("incorrect password.");
            }
        } catch (AccountPersistException e) {
            throw new AccountServiceException("Can't log in");
        }
    }

    public AccountPersistService getAccountPersistService() {
        return accountPersistService;
    }

    public void setAccountPersistService(AccountPersistService accountPersistService) {
        this.accountPersistService = accountPersistService;
    }

    public AccountEmailService getAccountEmailService() {
        return accountEmailService;
    }

    public void setAccountEmailService(AccountEmailService accountEmailService) {
        this.accountEmailService = accountEmailService;
    }

    public AccountCaptchaService getAccountCaptchaService() {
        return accountCaptchaService;
    }

    public void setAccountCaptchaService(AccountCaptchaService accountCaptchaService) {
        this.accountCaptchaService = accountCaptchaService;
    }
}
