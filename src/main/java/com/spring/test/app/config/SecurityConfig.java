package com.spring.test.app.config;

import com.spring.test.app.exception.SecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.List;

@KeycloakConfiguration
@RequiredArgsConstructor
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private final SecurityProblemSupport problemSupport;

    private final SecurityExceptionHandler securityExceptionHandler;

    @Bean
    @Override
    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
        var filter = new KeycloakAuthenticationProcessingFilter(this.authenticationManagerBean());
        filter.setSessionAuthenticationStrategy(this.sessionAuthenticationStrategy());
        filter.setAuthenticationFailureHandler(securityExceptionHandler);
        return filter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.headers()
                .defaultsDisabled()
                .cacheControl();

        http.cors()
                .configurationSource(r -> {
                    var config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(CorsConfiguration.ALL));
                    config.setAllowedMethods(List.of(
                            HttpMethod.GET.name(), HttpMethod.POST.name(),
                            HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
                    config.setAllowedHeaders(List.of(CorsConfiguration.ALL));
                    return config;
                });

        http.exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport);

        http
                .csrf().disable()
                .httpBasic().disable()
                .requestCache().disable()
                .formLogin().disable()
                .logout().disable()
                .rememberMe().disable();

        var s = new DefaultWebSecurityExpressionHandler();
        s.setDefaultRolePrefix("");
        http.authorizeRequests()
                .expressionHandler(s)
                .mvcMatchers("/public-api/**").permitAll()
                .mvcMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
                .expressionHandler(s);
    }


}
