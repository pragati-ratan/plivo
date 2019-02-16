package com.plivo.contactBook.security.models;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private CurrentUserContext userContext;

    public UserAuthenticationToken(CurrentUserContext userContext){
        super(userContext.getAuthorities());
        this.userContext = userContext;
    }

    @Override
    public boolean isAuthenticated(){
        return true;
    }

    @Override
    public UserDetails getPrincipal(){
        return this.userContext;
    }

    @Override
    public Object getCredentials(){
        return null;
    }

}
