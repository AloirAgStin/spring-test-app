package com.spring.test.app.controller;

import com.spring.test.app.model.TokenDto;
import com.spring.test.app.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PrivateController {

    @GetMapping(path = "/token")
    public TokenDto getUserToken() {
        return getToken();
    }

    @GetMapping(path = "/token/user")
    @PreAuthorize("hasRole('ROLE_customer')")
    public TokenDto getAdminToken() {
        return getToken();
    }

    @GetMapping(path = "/token/user2")
    @PreAuthorize("hasRole('ROLE_customer2')")
    public TokenDto getAdminToken2() {
        return getToken();
    }

    private TokenDto getToken() {
        return new TokenDto()
                .setUser(SecurityUtil.getUserId())
                .setToken(SecurityUtil.getUserToken())
                .setAuthorities(SecurityUtil.getUserAuthorities());
    }

}
