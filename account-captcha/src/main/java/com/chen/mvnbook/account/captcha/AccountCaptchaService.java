package com.chen.mvnbook.account.captcha;

import java.util.List;

/**
 * Created by YouZeng on 2015-09-22.
 */
public interface AccountCaptchaService {

    String generateCaptchaKey() throws AccountCaptchaException;
    byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;
    boolean validateCaptcha(String captchaKey, String captcha) throws AccountCaptchaException;
    List<String> getPreDefinedTexts();
    void setPreDefinedTexts(List<String> preDefinedTexts);
}
