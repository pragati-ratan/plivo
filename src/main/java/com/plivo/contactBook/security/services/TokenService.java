package com.plivo.contactBook.security.services;

import com.plivo.contactBook.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${token.secret}")
    private String tokenSecret;

    public String generateToken(String username){
        return Constants.Token.TOKEN_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.Token.EXPIRY))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();
    }

    public String validateToken(String token) throws SignatureException, ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token.replace(Constants.Token.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

}
