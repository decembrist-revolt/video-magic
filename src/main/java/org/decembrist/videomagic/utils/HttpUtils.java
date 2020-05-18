package org.decembrist.videomagic.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtils {

    public static String ACCESS_TOKEN_COOKIE_KEY = "access_token";

    private HttpUtils() {
    }

    public static void removeAccessToken(HttpServletResponse response) {
        final var cookie = new Cookie(ACCESS_TOKEN_COOKIE_KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static String getAccessToken(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(ACCESS_TOKEN_COOKIE_KEY)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
