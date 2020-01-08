package com.spring.manager.security;

import com.spring.manager.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.spring.manager.security.SecurityConstants.EXPIRATION_TIME;
import static com.spring.manager.security.SecurityConstants.SECRET;

@Component
public class JWTTokenProvider {
    // generate token
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String userId = Long.toString(user.getId());

        Map<String, Object> claims = createClaims(user);

        Date now = new Date(System.currentTimeMillis());
        Date expireDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", Long.toString(user.getId()));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());
        return claims;
    }

    // validate token

    // get user id from token
}
