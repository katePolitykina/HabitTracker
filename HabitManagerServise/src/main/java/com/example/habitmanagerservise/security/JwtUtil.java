package com.example.habitmanagerservise.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String jwtSecret = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret"; // Убедитесь, что этот ключ совпадает с ключом из первого проекта

    public Claims extractClaims(String token) {
        try {
            System.out.println("extractClaims" );
            return Jwts.parser()
                    .setSigningKey(jwtSecret)  // Тот же ключ для проверки подписи
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            System.out.println("SignatureException" + e.getMessage());
            throw new RuntimeException("Invalid JWT signature");
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    public boolean isTokenValid(String token) {
        try {
            System.out.println("isTokenValid");
            extractClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("isTokenValid Exception" + e.getMessage());
            return false;
        }
    }
}
