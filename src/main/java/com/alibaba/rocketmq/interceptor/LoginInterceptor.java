package com.alibaba.rocketmq.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    private String getSSOLoginURL(HttpServletRequest request) throws IOException {
        return  "https://" + request.getServerName()
                + ":" + System.getProperty("httpsPort", "443")
                + "/cockpit/login?redirect="
                + URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
    }


    private String getAuthURL(HttpServletRequest request) {
        if (request.getScheme().contains("https"))
            return "http://" + request.getServerName()
                    + ":" + System.getProperty("httpPort", "80") + "/rocketmq/sso/";
        return  request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + "/rocketmq/sso/";
    }


    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //Check if already logged in or not.
        HttpSession session = request.getSession();

        if (null != session.getAttribute(Helper.LOGIN_KEY)) {
            return true;
        }


        //Check if the request is a callback from SSO.
        String token = request.getParameter(Helper.TOKEN_KEY);
        if (null == token || token.isEmpty()) {
            response.sendRedirect(getSSOLoginURL(request));
            return false;
        } else {
            URL url = new URL(getAuthURL(request) + token);
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(3000);
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();

                if (HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {
                    InputStream inputStream = urlConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) > 0) {
                        byteArrayOutputStream.write(buffer, 0, len);
                    }

                    Login login = JSON.parseObject(new String(byteArrayOutputStream.toByteArray(), "UTF-8"),
                            Login.class);

                    //TODO validate more.
                    request.getSession().setAttribute(Helper.LOGIN_KEY, login);
                    return true;
                } else {
                    LOGGER.error("Invoking Cockpit SSO, response status NOT OK. Status {}",
                            urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                LOGGER.error("Invoking Cockpit SSO failed.", e);
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        }

        response.sendRedirect(getSSOLoginURL(request));
        return false;
    }
}
