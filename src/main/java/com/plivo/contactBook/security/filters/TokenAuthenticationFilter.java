package com.plivo.contactBook.security.filters;

import com.plivo.contactBook.cache.models.CurrentUser;
import com.plivo.contactBook.cache.services.UserCache;
import com.plivo.contactBook.security.models.CurrentUserContext;
import com.plivo.contactBook.security.models.UserAuthenticationToken;
import com.plivo.contactBook.security.services.TokenService;
import com.plivo.contactBook.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UserCache userCache;

    public TokenAuthenticationFilter(TokenService tokenService, UserCache userCache){
        this.tokenService = tokenService;
        this.userCache = userCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(Constants.Token.HEADER_STRING);
        if(header == null || !header.startsWith(Constants.Token.TOKEN_PREFIX)){
            chain.doFilter(req, res);
            return;
        }
        try {
            UserAuthenticationToken authenticationToken = getAuthentication(header);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Invalid Token");
        }
        chain.doFilter(req, res);
    }

    private UserAuthenticationToken getAuthentication(String header) throws SignatureException, ExpiredJwtException {
        if(header != null) {

            String username = tokenService.validateToken(header);
            if (username != null) {
                CurrentUser user = userCache.fetchUserInfoFromCache(username);
                if(user == null){
                    return null;
                }

                CurrentUserContext context= CurrentUserContext.createUserContext(
                        username,
                        null,
                        Constants.Roles.USER,
                        user.getId());
                return new UserAuthenticationToken(context);
            }
            else{
                return null;
            }
        }
        return null;
    }
}
