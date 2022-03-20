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

    @GetMapping(path = "/token/user")
    @PreAuthorize("hasRole('ROLE_role-user')")
    public TokenDto getUserToken() {
        return new TokenDto()
                .setUser(SecurityUtil.getUserId())
                .setToken(SecurityUtil.getUserToken())
                .setAuthorities(SecurityUtil.getUserAuthorities());
    }

    @GetMapping(path = "/token/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public TokenDto getAdminToken() {
        return new TokenDto()
                .setUser(SecurityUtil.getUserId())
                .setToken(SecurityUtil.getUserToken())
                .setAuthorities(SecurityUtil.getUserAuthorities());
    }

}
