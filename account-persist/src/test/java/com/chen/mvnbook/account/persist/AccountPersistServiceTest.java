package com.chen.mvnbook.account.persist;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by YouZeng on 2015-09-10.
 */
public class AccountPersistServiceTest {

    private AccountPersistService accountPersistService;
    private static final String DATA_PATH = "target/test-classes/persist-data.xml";

    @Before
    public void prepare() throws AccountPersistException {
        File persistDataFile = new File(DATA_PATH);
        if(persistDataFile.exists()){
            persistDataFile.delete();
        }
        ApplicationContext context = new ClassPathXmlApplicationContext("account-persist.xml");
        accountPersistService = (AccountPersistService)context.getBean("accountPersistService");

        Account account = new Account("chen", "youzeng", "409121961@qq.com", "fuck you", true);
        accountPersistService.createAccount(account);
    }

    @Test
    public void testReadAccount() throws AccountPersistException {
        Account account = accountPersistService.readAccount("chen");
        assertNotNull(account);
        assertEquals("youzeng", account.getName());
        assertEquals("409121961@qq.com", account.getEmail());
        assertEquals("fuck you", account.getPassword());
        assertTrue(account.isActivated());
    }

    @Test
    public void testCreateAccount() throws AccountPersistException {
        Account account = new Account("chen2", "youzeng2", "409121961@qq.com2", "fuck you2", false);
        accountPersistService.createAccount(account);

        assertNotNull(account);
    }
}
