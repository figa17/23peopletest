package com.demo.test.auth;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
public interface InternalValidationFilter {

    void authInternalValidation(Claims claims);

    boolean existJwtToken(HttpServletRequest request, HttpServletResponse res);

    Claims validateToken(HttpServletRequest request);
}
