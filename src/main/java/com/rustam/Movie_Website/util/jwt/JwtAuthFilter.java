package com.rustam.Movie_Website.util.jwt;

import com.rustam.Movie_Website.dto.request.RefreshRequest;
import com.rustam.Movie_Website.service.UserDetailsServiceImpl;
import com.rustam.Movie_Website.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getTokenFromRequest(request);
        String refreshToken = "";
        if (token != null){
            if (!jwtUtil.isValidUserIdFromToken(token)) {
                refreshToken = String.valueOf(userService.refreshToken(new RefreshRequest(token)));
            }else {
                refreshToken = token;
            }
        }

        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            String userIdAsUsername = jwtUtil.getUserIdAsUsernameFromToken(refreshToken);
            log.info("username {}",userIdAsUsername);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userIdAsUsername);

            if (userDetails != null/* && utilService.getCurrentUserByEmailForAllRole(email).isActivated()*/) {
                System.out.println("UserDetails of current user: " + userDetails);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
