package com.demo.test.service;

import com.demo.test.config.ConfToken;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@Service
public class TokenImpService implements TokenService {

    private static final String SECRET_KEY = "test_23people_23people_23people";
    private static final String SUBJECT = "23people";

    @Override
    public String getJwtToken() {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(SUBJECT)
                .setSubject(SUBJECT)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000000))
                .signWith(ConfToken.generalKey()).compact();

        return "Bearer " + token;
    }
}
