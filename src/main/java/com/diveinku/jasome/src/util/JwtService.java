package com.diveinku.jasome.src.util;

import com.diveinku.jasome.src.exception.member.EmptyJwtException;
import com.diveinku.jasome.src.exception.member.ExpiredJwtException;
import com.diveinku.jasome.src.exception.member.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Long JWT_ACCESS_TOKEN_TIME;
    private final String JWT_ACCESS_SECRET_KEY;
    private Key accessKey;

    public JwtService(@Value("${jwt.time.access}") Long JWT_ACCESS_TOKEN_TIME,
                      @Value("${jwt.secret.access}") String JWT_ACCESS_SECRET_KEY) {
        this.JWT_ACCESS_TOKEN_TIME = JWT_ACCESS_TOKEN_TIME;
        this.JWT_ACCESS_SECRET_KEY = JWT_ACCESS_SECRET_KEY;
        this.accessKey = Keys.hmacShaKeyFor(JWT_ACCESS_SECRET_KEY.getBytes());
    }

    public String createJwt(long memberId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("memberId", memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JWT_ACCESS_TOKEN_TIME))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Header Authorization에서 JWT 받아온다.
    public String getJwtFromHeader(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    public long getMemberIdFromJwt() throws ExpiredJwtException, InvalidJwtException {
        // 1. 추출
        String accessToken = getJwtFromHeader();
        if (accessToken == null || accessToken.length() == 0){
            throw new EmptyJwtException();
        }
        // 2. 파싱
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(accessKey).build()
                    .parseClaimsJws(accessToken);
        }catch(ExpiredJwtException e){
            throw new ExpiredJwtException();
        }catch(Exception e){
            throw new InvalidJwtException();
        }
        return claims.getBody().get("memberId", Long.class);
    }

}
