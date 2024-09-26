package com.example.stayease.config.jwtService;


import com.example.stayease.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class JwtService {

    private String secreteKey;

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGen.generateKey();
        setSecreteKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

    }

    public String generateToken(User user){

        Map<String , Object> claims = new HashMap<>();

        claims.put("firstName",user.getFirstName());
        claims.put("lastName",user.getLastName());
        claims.put("role",user.getRole());

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000) )
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(getSecreteKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {

        return  Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token);

            return true;

        }catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        return false;
    }
}
