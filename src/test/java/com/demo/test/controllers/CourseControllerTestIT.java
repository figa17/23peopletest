package com.demo.test.controllers;

import com.demo.test.models.Course;
import com.demo.test.service.CourseService;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Felipe González Alfaro on 20-03-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureTestDatabase
public class CourseControllerTestIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CourseService courseService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        Faker faker = new Faker();
        int courses_size = 20;
        for (int i = 0; i <= courses_size; i++) {

            Course course = Course.builder()
                    .id(i)
                    .code(faker.code().ean8().substring(0, 4))
                    .name(faker.name().name())
                    .build();
            this.courseService.saveCourse(course);
        }
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(wac.getBean("courseController"));
    }


    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/courses/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(20)));

    }


    @Test
    public void getAllPag() throws Exception {
        this.mockMvc.perform(get("/courses").param("page","1").param("size","5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("size", is(5)))
                .andExpect(jsonPath("number", is(1)))
                .andExpect(jsonPath("totalPages", is(4)))
                .andExpect(jsonPath("totalElements", is(20)));

    }
}
