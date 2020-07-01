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

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class JwtTokenUtilsTest {

    @Test
    public void printKey(){
        SecretKey key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = Jwts.builder()
                .setSubject("chen")
                .signWith(key)
                .compact();
        log.info("token:{} " + token);

    }

}
