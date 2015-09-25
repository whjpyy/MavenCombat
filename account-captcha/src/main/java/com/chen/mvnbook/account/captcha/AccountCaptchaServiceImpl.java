package com.chen.mvnbook.account.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by YouZeng on 2015-09-23.
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean{
    private DefaultKaptcha producer;
    private Map<String, String> captchaMap = new HashMap<String, String>();
    private List<String> predefinedTexts;
    private int textCount = 0;

    @Override
    public String generateCaptchaKey() throws AccountCaptchaException {
        String key = RandomGenerator.getRandomString();
        String value = getCaptchaText();
        captchaMap.put(key, value);
        return key;
    }

    private String getCaptchaText(){
        if(predefinedTexts != null && !predefinedTexts.isEmpty()){
            String text = predefinedTexts.get(textCount);
            textCount = (textCount + 1) % predefinedTexts.size();
            return text;
        }else {
            return producer.createText();
        }
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if( text == null){
            throw new AccountCaptchaException("Captch key'" + captchaKey + "'not found!");
        }
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            throw new AccountCaptchaException("Failed to write captcha stream", e);
        }
        return out.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captcha) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if(text == null){
            throw new AccountCaptchaException("Captcha key'" + "' not found!");
        }
        if(text.equals(captcha)){
            captchaMap.remove(captchaKey);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<String> getPreDefinedTexts() {
        return this.predefinedTexts;
    }

    @Override
    public void setPreDefinedTexts(List<String> preDefinedTexts) {
        this.predefinedTexts = preDefinedTexts;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultKaptcha();
        producer.setConfig(new Config(new Properties()));
    }
}
