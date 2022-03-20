package com.spring.test.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class SecurityExceptionHandler implements SecurityAdviceTrait {

    @Override
    public ResponseEntity<Problem> handleAccessDenied(AccessDeniedException ex, NativeWebRequest request) {
        return (ex instanceof AccessDeniedProblem)
                ? create(((AccessDeniedProblem) ex).getCause(), request)
                : create(Status.FORBIDDEN, ex, request);
    }

    @Override
    public ResponseEntity<Problem> handleAuthentication(AuthenticationException ex, NativeWebRequest request) {
        return (ex instanceof AuthenticationProblem)
                ? create(((AuthenticationProblem) ex).getCause(), request)
                : create(Status.UNAUTHORIZED, ex, request);
    }

}
