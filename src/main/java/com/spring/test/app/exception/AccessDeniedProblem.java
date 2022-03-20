package com.spring.test.app.exception;

import org.springframework.security.access.AccessDeniedException;
import org.zalando.problem.ThrowableProblem;

public class AccessDeniedProblem extends AccessDeniedException {

    public AccessDeniedProblem(ThrowableProblem cause) {
        super(cause.getMessage(), cause);
    }

    @Override
    public ThrowableProblem getCause() {
        return (ThrowableProblem) super.getCause();
    }

}
