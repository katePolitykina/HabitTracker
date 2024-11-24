package com.example.habitmanagerservise.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;

    public CustomAuthenticationToken(String username) {
        super(null);
        this.username = username;
        setAuthenticated(true); // Отмечаем токен как аутентифицированный.
    }

    @Override
    public Object getCredentials() {
        return null; // Пароль не используется, только токен.
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
