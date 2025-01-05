package com.example.filter;

import com.example.dto.ErrorDto;
import com.example.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    private final String[] WHITE_LIST_ENDPOINTS = new String[]{"/api/login/**", "/api/signup/**", "/api/tokens/**", "/api/roles/**"};

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT AUTHENTICATION FILTER");

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("No Token, or Doesn't start with 'Bearer'");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorizationHeader.substring(7);
        Claims claims = null;
        UserDetails user = null;

        try {
            claims = jwtUtil.verifyAccessToken(accessToken);
            String email = jwtUtil.getSubject(claims);
            user = userDetailsService.loadUserByUsername(email);
        } catch (Exception ex) {
            System.out.println("Invalid Token");
            sendAuthenticationErrorResponse(request, response, ex);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            System.out.println("Successfully Authenticated");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRequestURI = request.getRequestURI();
        return Arrays.stream(WHITE_LIST_ENDPOINTS).anyMatch(endpoint -> endpoint.contains(currentRequestURI));
    }

    public void sendAuthenticationErrorResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception ex
    ) throws IOException {
        System.out.println("exception ${ex::class}");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), new ErrorDto("Unauthorized: " + ex.getMessage()));
    }
}