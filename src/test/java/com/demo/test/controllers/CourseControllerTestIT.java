package com.demo.test.controllers;

import com.demo.test.models.Course;
import com.demo.test.service.StandarService;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
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
    @Qualifier("courseservice")
    private StandarService courseService;

    @Autowired
    private Gson gson;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        Faker faker = new Faker();
        int courses_size = 20;
        Course course;
        course = Course.builder()
                .id(1)
                .code("0001")
                .name("course_1")
                .build();

        this.courseService.save(course);

        course = Course.builder()
                .id(2)
                .code("0002")
                .name("course_2")
                .build();

        this.courseService.save(course);

        for (int i = 3; i <= courses_size; i++) {

            course = Course.builder()
                    .id(i)
                    .code(faker.code().ean8().substring(0, 4))
                    .name(faker.name().name())
                    .build();
            this.courseService.save(course);
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
        this.mockMvc.perform(get("/courses").param("page", "1").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("size", is(5)))
                .andExpect(jsonPath("number", is(1)))
                .andExpect(jsonPath("totalPages", is(5)))
                .andExpect(jsonPath("totalElements", is(23)));

    }

    @Test
    public void getCourse() throws Exception {

        this.mockMvc.perform(get("/courses/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("course_2")))
                .andExpect(jsonPath("$.code", is("0002")))
                .andExpect(jsonPath("$.id", is(2)));

    }

    @Test
    public void getCourseNotFound() throws Exception {

        this.mockMvc.perform(get("/courses/999999"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createCourse() throws Exception {

        Course course = Course.builder()
                .id(22)
                .code("0022")
                .name("course_22")
                .build();


        this.mockMvc.perform(post("/courses")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(course)))
                .andDo(print())
                .andExpect(status().isCreated());


        this.mockMvc.perform(get("/courses/22"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("course_22")))
                .andExpect(jsonPath("$.code", is("0022")))
                .andExpect(jsonPath("$.id", is(22)));

    }

    @Test
    public void createCourseErrorContentType() throws Exception {

        Course course = Course.builder()
                .id(22)
                .code("0022")
                .name("course_22")
                .build();


        this.mockMvc.perform(post("/courses")
                .content(this.gson.toJson(course)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void createCourseErrorCourse() throws Exception {

        Course course = Course.builder()
                .id(22)
                .code("0022_error")
                .name("course_22")
                .build();


        this.mockMvc.perform(post("/courses")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(course)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("errors[0]", is("Code had max 4 chars")));
    }

    @Test
    public void updateCourse() throws Exception {

        Course course = Course.builder()
                .name("name_updated")
                .code("0023")
                .build();

        this.mockMvc.perform(put("/courses/1")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(course)))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/courses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name_updated")))
                .andExpect(jsonPath("$.code", is("0023")))
                .andExpect(jsonPath("$.id", is(1)));

    }


    @Test
    public void updateCourseError() throws Exception {

        Course course = Course.builder()
                .code("code")
                .name("name_updated")
                .build();


        this.mockMvc.perform(put("/courses/9999")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(course)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCourse() throws Exception {


        this.mockMvc.perform(delete("/courses/1")
                .header("Content-Type", "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCourseError() throws Exception {


        this.mockMvc.perform(delete("/courses/9999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
