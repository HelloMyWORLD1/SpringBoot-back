package com.helloworld.v1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class SecurityService {
    private static final String SECRET_KEY = "ajifojwiofwfwaffejwiofjewiofojwkfdjfeiowjf";

    //로그인 서비스
    public String createToken(String subject, long expTime){
        if(expTime <= 0){
            throw new RuntimeException("만료 시간이 0보다 커야합니다.");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY); //String -> Byte 변환
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    //토큰 검증 (boolean return 해서 사용)
    public String getSubject(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
