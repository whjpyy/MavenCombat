package com.chen.mvnbook.account;

import com.chen.mvnbook.account.captcha.AccountCaptchaService;
import com.chen.mvnbook.account.service.AccountService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YouZeng on 2015-10-21.
 */
public class AccountServiceTest {

    private GreenMail greenMail;
    private AccountService accountService;

    @Before
    public void prepare() throws Exception{
        String[] springConfigFiles = {"account-email.xml", "account-persist.xml"
                , "account-captha.xml", "account-service.xml"
        };
        ApplicationContext ctx = new ClassPathXmlApplicationContext(springConfigFiles);
        AccountCaptchaService accountCaptchaService = (AccountCaptchaService)ctx.getBean("accountCaptchaService");
        List<String> preDefinedTexts = new ArrayList<String>();
        preDefinedTexts.add("12345");
        preDefinedTexts.add("abcde");
        accountCaptchaService.setPreDefinedTexts(preDefinedTexts);

        accountService = (AccountService)ctx.getBean("accountService");

        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("whjpyy@sina.com", "13547189952");
        greenMail.start();
    }

    @Test
    public void testAccountService() throws Exception {
        // 1. Get captcha
        String captchaKey = accountService.generateCaptchaKey();
        accountService.generateCaptchaImage(captchaKey);
        String captchaValue = "12345";
    }

    @After
    public void stopMailServer() throws Exception {
        greenMail.stop();
    }
}
