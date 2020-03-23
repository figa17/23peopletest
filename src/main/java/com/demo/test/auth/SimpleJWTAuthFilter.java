package com.demo.test.auth;


import com.demo.test.config.ConfToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
@Slf4j
public class SimpleJWTAuthFilter extends OncePerRequestFilter implements InternalValidationFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (existJwtToken(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    this.authInternalValidation(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    /**
     * @param claims
     */
    @Override
    public void authInternalValidation(Claims claims) {
        List<String> authorities = (List) claims.get("authorities");

        AbstractAuthenticationToken auth = new AnonymousAuthenticationToken(claims.getSubject(), "JWT_23_peeople",
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

        log.info(auth.toString());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    /**
     * Confirm if exist token inside the @{@link HttpServletRequest}.
     *
     * @param request {@link HttpServletRequest}
     * @param res     {@link HttpServletResponse}
     * @return boolean confirm token
     */
    @Override
    public boolean existJwtToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    /**
     * Method to get jwt content from @{@link HttpServletRequest}.
     *
     * @param request
     * @return Claim with valid token
     */
    @Override
    public Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(ConfToken.generalKey()).parseClaimsJws(jwtToken).getBody();
    }
}
