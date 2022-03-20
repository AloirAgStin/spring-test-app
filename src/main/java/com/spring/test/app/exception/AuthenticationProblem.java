package com.spring.test.app.exception;

import org.springframework.security.core.AuthenticationException;
import org.zalando.problem.ThrowableProblem;

public class AuthenticationProblem extends AuthenticationException {

    public AuthenticationProblem(ThrowableProblem cause) {
        super(cause.getMessage(), cause);
    }

    @Override
    public ThrowableProblem getCause() {
        return (ThrowableProblem) super.getCause();
    }

}
