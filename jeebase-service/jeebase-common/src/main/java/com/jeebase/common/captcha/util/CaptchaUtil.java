package com.jeebase.common.captcha.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class CaptchaUtil {

    public static boolean isVerified(String verKey, String verCode, HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        String cacheVerCode = (String) servletContext.getAttribute("code_" + verKey);
        return verCode.equalsIgnoreCase(cacheVerCode);
    }
}