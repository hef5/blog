package com.hef.blog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DynamicTest;

import javax.crypto.SecretKey;

@Log4j2
public class JwtTokenUtils {

    private static SecretKey KEY= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(KEY)
                .compact();
        return token;
    }


    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }


    private static Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证JWT
     */
    public Boolean validateToken(String token, String username) {

        String username1 = getUsernameByToken( token );

        return username.equals(username1);

    }

}
