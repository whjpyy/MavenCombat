<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.chen.mvnbook.account.service.AccountService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        .text-field {position: absolute; left: 40%; background-color:rgb(255,230,220);}
        label {display: inline-table; width: 90px; margin: 0px 0px 10px 20px; }
        input {display: inline-table; width: 150px; margin: 0px 20px 10px 0px;}
        img {width:150px; margin: 0px 20px 10px 110px;}
        h2 {margin: 20px 20px 20px 40px;}
        button {margin: 20px 20px 10px 110px}
    </style>
</head>
<body>
    <%
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletConfig().getServletContext());
        AccountService accountService = (AccountService)context.getBean("accountService");
        String captchaKey = accountService.generateCaptchaKey();
    %>
    <div class="text-field">
        <h2>注册新用户abc</h2>
        <form name="signup" action="signup" method="post">
            <label>账号ID：</label><input name="id" /><br />
            <label>Email：</label><input name="email" /><br />
            <label>显示名称：</label><input name="name" /><br />
            <label>密码：</label><input name="password" /><br />
            <label>确认密码：</label><input name="comfirm_password" /><br />
            <label>验证码：</label><input name="captcha_value" /><br />
            <input type="hidden" name="captcha_key" value="<%=captchaKey%>" />
            <img src="<%=request.getContextPath()%>/captcha_image?key=<%=captchaKey%>" /><br />
            <button>确认并提交</button>
        </form>
    </div>
</body>
</html>
