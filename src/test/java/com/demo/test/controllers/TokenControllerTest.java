package com.demo.test.controllers;

import com.demo.test.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@WebMvcTest(TokenController.class)
class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TokenService tokenService;

    @Test
    void getUrl() throws Exception {

        this.mockMvc.perform(get("/token")).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getToken() throws Exception {
        when(tokenService.getToken()).thenReturn("token");

        this.mockMvc.perform(get("/token")).andDo(print()).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(containsString("token")));
    }
}