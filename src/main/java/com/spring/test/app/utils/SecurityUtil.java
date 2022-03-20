package com.spring.test.app.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SecurityUtil {

    public static String getUserId() {
        return getAuthenticationContextPrincipal().getName();
    }

    public static String getUserToken() {
        return getAuthenticationContextPrincipal().getKeycloakSecurityContext().getTokenString();
    }

    public static String getUserPrincipal() {
        return getAuthenticationContextPrincipal().getKeycloakSecurityContext().getTokenString();
    }

    public static List<String> getUserAuthorities() {
        return getAuthenticationContext().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private static KeycloakPrincipal<KeycloakSecurityContext> getAuthenticationContextPrincipal() {
        return (KeycloakPrincipal<KeycloakSecurityContext>) getAuthenticationContext().getPrincipal();
    }

    @SneakyThrows
    private static KeycloakAuthenticationToken getAuthenticationContext() {
        if (SecurityContextHolder.getContext() == null ||
                !(SecurityContextHolder.getContext().getAuthentication() instanceof KeycloakAuthenticationToken)) {
            throw new CredentialNotFoundException("Authentication context is empty");
        }
        return (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

}
