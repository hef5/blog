package com.hef.blog;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class JwtTokenUtilsTest {

    private static String strKey = "iPrBkYvpb3ddf2FtGEZ-GzMTQKhrUzJzyNM6RKpZHMf";

    @Test
    public void printKey(){
        Key key = Keys.hmacShaKeyFor(strKey.getBytes());


        String token = Jwts.builder()
                .setSubject("chen")
                .signWith(key)
                .compact();
        log.info("key:{} " + key);
        log.info("token:{} " + token);

        String sub =Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
        log.info("sub: " + sub);



    }

}

// LiQw7BZmeX0zO+jO49F3NNIXtFkKJadR+7kOA5rkxjw=
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGVuIn0.HpnluN9UahPBDh6VfV2vKjaAafVrtcquEPSycwc3cnw


// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGVuIn0.HpnluN9UahPBDh6VfV2vKjaAafVrtcquEPSycwc3cnw