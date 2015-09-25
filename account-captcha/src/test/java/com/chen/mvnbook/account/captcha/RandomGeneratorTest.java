package com.chen.mvnbook.account.captcha;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by YouZeng on 2015-09-23.
 */
public class RandomGeneratorTest {

    @Test
    public void testGetRandomString(){
        Set<String> randoms = new HashSet<String>(100);
        for(int i = 0;i < 100;i ++){
            String random = RandomGenerator.getRandomString();
            Assert.assertFalse(randoms.contains(random));
            randoms.add(random);
        }
    }
}
