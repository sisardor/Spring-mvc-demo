package com.blackiceinc.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

public final class CsrfTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        System.out.println("\n\n\n");
        System.out.println(token.getToken());
        
        // Spring Security will allow the Token to be included in this header name
        response.setHeader("X-CSRF-HEADER", token.getHeaderName());
 
        // Spring Security will allow the token to be included in this parameter name X-XSRF-TOKEN
        response.setHeader("X-CSRF-PARAM", token.getParameterName());
 
        // this is the value of the token to be included as either a header or an HTTP parameter
        response.setHeader("X-CSRF-TOKEN", token.getToken());
 
        filterChain.doFilter(request, response);
    }
}