package com.flamelab.shopserver.configs;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomIpAuthenticationProvider implements AuthenticationProvider {

    Set<String> whitelist = new HashSet<>();

    public CustomIpAuthenticationProvider() {
        whitelist.add("wardek.tk");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String userIp = details.getRemoteAddress();
        if (!whitelist.contains(userIp)) {
            throw new BadCredentialsException("Invalid IP Address");
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
