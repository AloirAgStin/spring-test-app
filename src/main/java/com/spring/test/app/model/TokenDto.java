package com.spring.test.app.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Accessors(chain = true)
public class TokenDto {

    @NotEmpty
    private String user;

    private String token;

    private List<String> authorities;

}
