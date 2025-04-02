package com.capstone.security.jwt;

import com.capstone.security.AppUserDetailsService;
import com.capstone.security.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private String secret;
    private long validityInSeconds;

    private SecretKey key;

    @Autowired
    private AppUserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secret,
                            @Value("${security.jwt.token.expire-length}") long validityInSeconds) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.validityInSeconds = validityInSeconds;
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.validityInSeconds * 1000);
        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key)
                .expiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
                .toList();
        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userPrincipal,token,authorities);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        }catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        }catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰 입니다.");
        }catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            e.printStackTrace();
        }
        return false;
    }

}
