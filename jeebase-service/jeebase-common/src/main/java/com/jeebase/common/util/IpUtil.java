package com.jeebase.common.util;

import com.jeebase.common.base.Constant;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author jeebase
 */
public class IpUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == Constant.Number.ZERO || Constant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == Constant.Number.ZERO || Constant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == Constant.Number.ZERO || Constant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (Constant.LOCALHOST_IP.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            /**
             * 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
             */
            if (ipAddress != null && ipAddress.length() > Constant.Number.FIFTEEN) {
                if (ipAddress.indexOf(Constant.DELIMITER_COMMA) > Constant.Number.ZERO) {
                    ipAddress = ipAddress.substring(Constant.Number.ZERO, ipAddress.indexOf(Constant.DELIMITER_COMMA));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();
        return ipAddress;
    }
}
