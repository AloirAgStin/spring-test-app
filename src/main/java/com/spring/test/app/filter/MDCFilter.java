package com.spring.test.app.filter;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class MDCFilter extends OncePerRequestFilter implements Ordered {

    public static final Map<String, String> mdcHeaders = Map.of(
            "x-b3-traceid", "traceId",
            "x-b3-spanid", "spanId"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            initResponse(response);
            filterChain.doFilter(request, response);
        } finally {
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    private void initResponse(HttpServletResponse response) {
        mdcHeaders.forEach((key, value) -> response.addHeader(key, MDC.get(value)));
    }
}
