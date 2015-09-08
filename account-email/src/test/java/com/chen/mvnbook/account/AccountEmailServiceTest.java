package com.chen.mvnbook.account;

import com.chen.mvnbook.account.email.AccountEmailException;
import com.chen.mvnbook.account.email.AccountEmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.Message;
import javax.mail.MessagingException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by YouZeng on 2015-09-07.
 */
public class AccountEmailServiceTest {

    private GreenMail greenMail;

    @Before
    public void startMailServer(){
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("youzeng.chen@maesinfo.cn", "cyz123654");
        greenMail.start();
    }

    @Test
    public void testSendMail() throws AccountEmailException, MessagingException, InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService = (AccountEmailService)context.getBean("accountEmailService");

        String subject = "Test Subject";
        String htmlText = "<h3>Test</h3>";
        accountEmailService.sendMail("youzeng.chen@maesinfo.cn", subject,htmlText);

        greenMail.waitForIncomingEmail(2000, 1);

        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(subject, messages[0].getSubject());
        assertEquals(htmlText, GreenMailUtil.getBody(messages[0]).trim());
    }

    @After
    public void stopMailServer(){
        greenMail.stop();
    }

}
