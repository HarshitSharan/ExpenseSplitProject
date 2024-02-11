package org.hsharan.userAuth;

import org.springframework.security.core.GrantedAuthority;

public class UserGrantedAuthorities implements GrantedAuthority {
    private static final String USER_AUTHORITY= "USER";
    @Override
    public String getAuthority() {
        return USER_AUTHORITY;
    }
}
