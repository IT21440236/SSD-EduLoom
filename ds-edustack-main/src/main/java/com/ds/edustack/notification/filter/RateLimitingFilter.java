package com.ds.edustack.notification.filter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimitingFilter implements Filter {

    private final Bucket bucket;

    public RateLimitingFilter() {
        // Configure the rate limit (e.g., 5 requests per minute)
        Bandwidth limit = Bandwidth.classic(2, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Log to verify the filter is applied
        System.out.println("Filter applied to: " + httpRequest.getRequestURI());

        // Check if the request is for the specific endpoint
        if (httpRequest.getRequestURI().contains("/api/v1/notifications/send-email")) {
            if (bucket.tryConsume(1)) {
                chain.doFilter(request, response); // Continue if allowed
            } else {
                httpResponse.setStatus(429); // Use 429 Too Many Requests
                httpResponse.getWriter().write("Too many requests. Please try again later.");
                return; // Exit filter chain
            }
        } else {
            // For other endpoints, allow normal flow
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}


