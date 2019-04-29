package com.noleggiobiciclette;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationSuccessListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    @Override
    @EventListener(value = {AuthorizedEvent.class})
    public void onApplicationEvent(ApplicationEvent event)
    {
        if (event instanceof InteractiveAuthenticationSuccessEvent) {
            Authentication auth =  ((InteractiveAuthenticationSuccessEvent)event).getAuthentication();
            logger.info("Login success: " + auth.getName() + ", details: " + event.toString());
        } else if (event instanceof AbstractAuthenticationFailureEvent) {
            logger.error("Login failed: " + event.toString());
        } else if (event instanceof AuthorizedEvent) {
            Authentication auth =  ((AuthorizedEvent)event).getAuthentication();
            logger.debug("Authorized: " + auth.getName() + ", details: " + event.toString());
        } else if (event instanceof AuthorizationFailureEvent) {
            Authentication auth =  ((AuthorizationFailureEvent)event).getAuthentication();
            logger.error("Authorization failed: " + auth.getName() + ", details: " + event.toString()+ " - ");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication.getAuthorities() + " - " +authentication.getName() +" - "+authentication.getPrincipal().toString());
        }
    }
}


// @Component
// public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
//     private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    // @Override
    // public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    //     Object userName = event.getAuthentication().getPrincipal();
    //     Object credentials = event.getAuthentication().getCredentials();
    //     logger.info("Failed login using USERNAME [" + userName + "]");
    //     logger.info("Failed login using PASSWORD [" + credentials + "]");
    // }
//}