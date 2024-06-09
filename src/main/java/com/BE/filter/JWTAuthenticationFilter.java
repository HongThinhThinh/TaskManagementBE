package com.BE.filter;


import com.BE.exception.exceptions.NotAllowException;
import com.BE.service.JWTService;
import com.BE.service.UserDetailsServiceImp;
import com.BE.utils.ResponseHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    UserDetailsServiceImp userDetail;

    // list danh sach api valid
    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/login",
            "/api/**"
    );


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println(uri);
        // hok can token
        if (isPermitted(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getToken(request);

        // khong co token
        if (token == null) {
            responseHandler.responseResolver(request, response, new NotAllowException("Token is null"));
            return;
        }
        // co token
        String username;
        try {
            // từ token tìm ra thằng đó là ai
            username = jwtService.extractUsername(token);
        } catch (ExpiredJwtException expiredJwtException) {
            // token het han
            responseHandler.responseResolver(request, response, new NotAllowException("Expired Token!"));
            return;
        } catch (MalformedJwtException malformedJwtException) {
            responseHandler.responseResolver(request, response, new NotAllowException("Invalid Token!"));
            return;
        }
        // token dung
        System.out.println(username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetail.loadUserByUsername(username);
            if (jwtService.isValid(token, userDetails))
            {
                UsernamePasswordAuthenticationToken
                        authenToken =
                        new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                authenToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenToken);
                // token ok, cho vao`
                filterChain.doFilter(request, response);
            }
        } else {
            // token xam`
            responseHandler.responseResolver(request, response, new NotAllowException("Invalid Token!"));
        }
    }


    private boolean isPermitted(String uri) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }


    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }


}
