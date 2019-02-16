package com.plivo.contactBook.security.models;

import com.plivo.contactBook.utils.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

public class CurrentUserContext implements UserDetails {

    private String username;

    private String password;

    private Integer id;

    private List<GrantedAuthority> authorities;

    private CurrentUserContext(String username,
                               String password,
                               List<GrantedAuthority> authorities,
                               int id){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.id = id;
    }

    public static CurrentUserContext createUserContext(String username,
                                                       String password,
                                                       String role,
                                                       int id){
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        return new CurrentUserContext(username, password, authorities, id);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        return this.authorities;
    }

    public int getId() { return this.id; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {return true; }

}
