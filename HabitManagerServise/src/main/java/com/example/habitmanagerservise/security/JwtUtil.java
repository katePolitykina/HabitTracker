package com.example.habitmanagerservise.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String jwtSecret = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret"; // Убедитесь, что этот ключ совпадает с ключом из первого проекта

    public Claims extractClaims(String token) {
        try {

            return Jwts.parser()
                    .setSigningKey(jwtSecret)  // Тот же ключ для проверки подписи
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            logger.info("extractClaims SignatureException" + e.getMessage());
            throw new RuntimeException("Invalid JWT signature");
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            logger.info("isTokenValid Exception" + e.getMessage());
            return false;
        }
    }
}
