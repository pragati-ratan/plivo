package com.plivo.contactBook.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plivo.contactBook.cache.models.CurrentUser;
import com.plivo.contactBook.cache.services.UserCache;
import com.plivo.contactBook.dto.ResponseDTO;
import com.plivo.contactBook.dto.UserCredentialsDTO;
import com.plivo.contactBook.security.models.CurrentUserContext;
import com.plivo.contactBook.security.services.TokenService;
import com.plivo.contactBook.utils.Constants;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserCache userCache;
    private ObjectMapper mapper;

    public LoginAuthenticationFilter(AuthenticationManager manager, TokenService tokenService, UserCache userCache, ObjectMapper mapper) {
        this.authenticationManager = manager;
        this.tokenService = tokenService;
        this.userCache = userCache;
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserCredentialsDTO user = mapper.readValue(req.getInputStream(), UserCredentialsDTO.class);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception e) {
            throw new UsernameNotFoundException(Constants.ErrorMessage.INVALID_CREDENTIALS);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {

        CurrentUserContext user = (CurrentUserContext) auth.getPrincipal();

        CurrentUser currentUser = new CurrentUser();
        currentUser.setUsername(user.getUsername());
        currentUser.setId(user.getId());
        currentUser.setRole(Constants.Roles.USER);
        userCache.addUserInfoInCache(currentUser);

        String token = tokenService.generateToken(user.getUsername());
        req.setAttribute(Constants.RequestAttributes.TOKEN, token);
        req.setAttribute(Constants.RequestAttributes.CONTEXT, user);

        chain.doFilter(req, res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(Constants.StatusCodes.UNAUTHORIZED);
        response.setMessage(Constants.ErrorMessage.INVALID_CREDENTIALS);

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(Constants.StatusCodes.UNAUTHORIZED);
        mapper.writeValue(res.getWriter(), response);
    }
}
