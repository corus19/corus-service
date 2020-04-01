package com.coronatracker.interceptors;

import com.coronatracker.security.SessionTokenProvider;
import com.google.common.base.Strings;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    @Autowired
    private SessionTokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ExecutionException {
        final String token = getJwtFromRequest(request);
        try{
            if(!Strings.isNullOrEmpty(token) && tokenProvider.validateToken(token)) {
                final String userIdFromToken = tokenProvider.getUserIdFromToken(token);
                request.setAttribute("userId", userIdFromToken);
            }
        } catch(Exception ex)  {
            logger.error(ex.getMessage(), ex);
        }
        return true;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
