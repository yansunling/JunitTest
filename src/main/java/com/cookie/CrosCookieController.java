package com.cookie;


import com.util.CommonUtil;
import com.yd.common.data.CIPResponseMsg;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RestController
@Component
public class CrosCookieController {


    @RequestMapping(value = "/corscookie")
    public CIPResponseMsg corscookie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CIPResponseMsg success = CommonUtil.success();


//        response.addHeader("Set-Cookie", "JSESSIONID=183D5590B6AAE5BCCFBE41A81DAD09D3; _jfinal_captcha=059ad8b438704a868f5497d6d47d7f6a; oripassword=13445AC455E8EDB1996BFB2CA0C78570; auth_sessionid=17f2a0359606461d9ffdbd736575484f; auth_sso_sessionid=17f2a0359606461d9ffdbd736575484f; cip_sso_token=9b1deddc-141b-4d8c-8595-240aae091787-1680069094021; orgid=25020203; net_org_id=250108010301");

        addOneCookie(response,"JSESSIONID=183D5590B6AAE5BCCFBE41A81DAD09D3; _jfinal_captcha=059ad8b438704a868f5497d6d47d7f6a; oripassword=13445AC455E8EDB1996BFB2CA0C78570; auth_sessionid=17f2a0359606461d9ffdbd736575484f; auth_sso_sessionid=17f2a0359606461d9ffdbd736575484f; cip_sso_token=9b1deddc-141b-4d8c-8595-240aae091787-1680069094021; orgid=25020203; net_org_id=250108010301","tlwl.uat.tuolong56.com");



        return success;
    }

    public static void addOneCookie(HttpServletResponse httpServletResponse, String cookie, String cookieDomain) {
        // Cookie过期时间
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 6); // 6小时后过期
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        System.out.println(sdf.format(date));
//        String responseHeaderString = cookieKey + "=" + cookieValue + ";";
        httpServletResponse.addHeader("Set-Cookie",cookie + ";Path=/; Domain=" + cookieDomain + ";"  + "; SameSite=None; Secure"); // Chrome浏览器跨域写Cookie需要带的属性
    }

}
