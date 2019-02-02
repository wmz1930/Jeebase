package com.jeebase.common.captcha.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeebase.common.captcha.GifCaptcha;

/**
 * @ClassName: CaptchaServlet
 * @Description: 验证码
 * @author jeebase-WANGLEI
 * @date 2018年6月11日 上午11:35:06
 */
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = -90304944339413093L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeKey = request.getParameter("codeKey");
        if (codeKey != null && !codeKey.trim().isEmpty()) {
            response.setContentType("image/gif");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            GifCaptcha captcha = new GifCaptcha(130, 38, 5);
            ServletContext servletContext = request.getSession().getServletContext();
            servletContext.setAttribute("code_" + codeKey, captcha.text().toLowerCase());
            captcha.out(response.getOutputStream());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}