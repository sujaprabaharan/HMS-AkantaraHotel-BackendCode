package com.akantara.AkantaraHotel.security;


import com.akantara.AkantaraHotel.service.CustomUserDetailsService;
import com.akantara.AkantaraHotel.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// JWT filter that runs once per HTTP request
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    // Injects the Utility class to handle JWT token generation and validation
    @Autowired
    private JWTUtils jwtUtils;

    // Loads user details from the database
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the incoming request
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // If the header is missing or empty, continue the request without authentication
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " prefix to get the JWT token
        jwtToken = authHeader.substring(7);

        // Extract the username/email from the token
        userEmail = jwtUtils.extractUsername(jwtToken);

        // If the user email exists and there is no current authentication, it loads  the user details from the database, validate the JWT token
        // After that, it creates new authentication object, and add additional details and then set the authentication in the Spring Security context for this request
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            if (jwtUtils.isValidToken(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        // Continue the filter chain for other filters or the target endpoint
        filterChain.doFilter(request, response);
    }

}
