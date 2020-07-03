package com.hef.blog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DynamicTest;

import javax.crypto.SecretKey;
import java.security.Key;

@Log4j2
public class JwtTokenUtils {

    private static final String STRINGKEY = "iPrBkYvpb3ddf2FtGEZ-GzMTQKhrUzJzyNM6RKpZHMf";


    public static String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(STRINGKEY.getBytes());
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .compact();
        return token;
    }


    public static String getUsernameByToken(String token) {
        String username =Jwts.parserBuilder()
                .setSigningKey(STRINGKEY)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
        return username;
    }


    public static Boolean validateToken(String token, String username) {
        String username1 = getUsernameByToken( token );
        return username.equals(username1);
    }

}
