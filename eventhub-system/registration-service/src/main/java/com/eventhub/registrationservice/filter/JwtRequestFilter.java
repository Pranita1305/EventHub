package com.eventhub.registrationservice.filter;

import com.eventhub.registrationservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(jwtUtil.isTokenValid(token)){
                Claims claims=jwtUtil.extractClaims(token);
                String role=claims.get("role",String.class);

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("role", role);
                request.setAttribute("userId", claims.get("userId"));
            }
        }

        chain.doFilter(request,response);
    }
}