package com.chen.mvnbook.account.web;

import com.chen.mvnbook.account.service.AccountService;
import com.chen.mvnbook.account.service.AccountServiceException;
import com.chen.mvnbook.account.service.SignUpRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by YouZeng on 2015-11-04.
 */
public class SignUpServlet extends HttpServlet {

    private static final long serialVersionUID = 2373780787957285102L;
    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_passord");
        String captchaKey = req.getParameter("captcha_key");
        String captchaValue = req.getParameter("captcha_value");

        if(id == null || id.length() == 0 ){
            resp.sendError(400, "Parameter Incomplete.");
            return;
        }

        AccountService service = (AccountService)context.getBean("accountService");
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setId(id);
        signUpRequest.setEmail(email);
        signUpRequest.setName(name);
        signUpRequest.setPassword(password);
        signUpRequest.setConfirmPassword(confirmPassword);
        signUpRequest.setCaptchaKey(captchaKey);
        signUpRequest.setCaptchaValue(captchaValue);

        signUpRequest.setActivateServiceUrl(getServletContext().getRealPath("/") + "activate");

        try{
            service.signUp(signUpRequest);
            resp.getWriter().print("Account is created. please check you mail box for activation link.");
        } catch (AccountServiceException e) {
            resp.sendError(400, e.getMessage());
            return;
        }
    }

}
