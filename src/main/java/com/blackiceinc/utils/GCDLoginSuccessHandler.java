package com.blackiceinc.utils;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class GCDLoginSuccessHandler implements
		AuthenticationSuccessHandler {
	protected Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,  HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		System.out.println("************************* Authentication success **********************************");
		handle(request, response, authentication);
        clearAuthenticationAttributes(request);
        request.getSession().setMaxInactiveInterval(60*60);
	}

	protected void handle(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to "
					+ targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	/** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication) {
        boolean ROLE_ADMIN = false;
        boolean ROLE_REGULATOR = false;
        boolean ROLE_USER = false;
        boolean ROLE_GUEST = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
            	ROLE_ADMIN = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_REGULATOR")) {
            	ROLE_REGULATOR = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
            	ROLE_USER = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_GUEST")) {
            	ROLE_GUEST = true;
                break;
            }
        }
 
        if (ROLE_ADMIN || ROLE_REGULATOR || ROLE_USER || ROLE_GUEST) {
            return "/main";
        } else {
            throw new IllegalStateException();
        }
        
        
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
