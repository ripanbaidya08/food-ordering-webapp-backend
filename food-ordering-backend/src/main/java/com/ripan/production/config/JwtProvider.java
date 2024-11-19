package com.ripan.production.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        // it  holds the user’s authorities, allowing for any subtype of GrantedAuthority.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        /**
         * takes the collection of GrantedAuthority objects and formats or processes them into a String representation,
         * such as a comma-separated list of roles or authorities (e.g., "ROLE_USER,ROLE_ADMIN").
         */
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder()
                .setIssuer("ripan") // sets the issuer claim (iss) of the JWT to "ripan". This claim is used to identify the entity that issued the token and can help in validating the authenticity of the token when received by another service.
                .setSubject("ripan@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", authentication.getName())
                .claim("roles", roles) // Ensure roles are included in the token
                .signWith(key)
                .compact();

        return jwt;
    }

    public String getEmailFromJwtToken(String jwt) {
        // Remove "Bearer " prefix if present and trim any surrounding whitespace
        if(jwt.startsWith("Bearer ")) jwt = jwt.substring(7).trim();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}