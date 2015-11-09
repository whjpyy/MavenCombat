package com.chen.mvnbook.account.web;

import com.chen.mvnbook.account.service.AccountService;
import com.chen.mvnbook.account.service.AccountServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by YouZeng on 2015-11-04.
 */
public class CaptchaImageServlet extends HttpServlet {
    private static final long serialVersionUID = 491101022643234776L;
    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");
//        System.out.println("key: " + key);
        if(key == null || key.length() == 0){
            resp.sendError(400, "No Captcha Key Found");
        }else{
            AccountService service = (AccountService)context.getBean("accountService");
            try{
                resp.setContentType("image/jpeg");
                OutputStream out = resp.getOutputStream();
                out.write(service.generateCaptchaImage(key));
            } catch (AccountServiceException e) {
                resp.sendError(404, e.getMessage());
            }
        }
    }
}
