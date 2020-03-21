package com.demo.test.controllers;

import com.demo.test.models.Course;
import com.demo.test.service.CourseService;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.Builder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import pojo.CoursePojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private Gson g;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;


    @Test
    void getCursesPagination() throws Exception {

        Faker faker = new Faker();

        Course course;
        int course_size = 5;
        List<Course> courseList = new ArrayList<>();

        for (int i = 0; i < course_size; i++) {

            course = Course.builder()
                    .id(i)
                    .code(faker.code().ean8().substring(0, 4))
                    .name(faker.name().name())
                    .build();
            courseList.add(course);
        }


        Page<Course> coursePage = new PageImpl<>(courseList, PageRequest.of(1, course_size), 5);

        when(this.courseService.getCoursesPag(anyInt(), anyInt())).thenReturn(coursePage);

        this.mockMvc.perform(get("/courses")
                .param("page", "1").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page", is(1)))
                .andExpect(jsonPath("totalPages", is(2)));

    }

    @Test
    void getAll() throws Exception {

        Course course = Course.builder()
                .name("test")
                .code("code")
                .id(1)
                .build();

        when(this.courseService.getCourses()).thenReturn(Collections.singletonList(course));

        this.mockMvc.perform(get("/courses/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].code", is("code")))
                .andExpect(jsonPath("$[0].id", is(1)));


    }

    @Test
    public void getAllEmptyError() throws Exception {

        when(this.courseService.getCourses()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/courses/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCourse() throws Exception {
        Course course = Course.builder()
                .name("test")
                .code("code")
                .id(1)
                .build();

        when(this.courseService.getCourseById(anyInt())).thenReturn(course);


        this.mockMvc.perform(get("/courses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.code", is("code")))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    public void getCourseNotFound() throws Exception {

        when(this.courseService.getCourseById(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/courses/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    void createCourse() throws Exception {

        when(this.courseService.saveCourse(any(CoursePojo.class))).thenReturn(true);

        CoursePojo pojo = new CoursePojo();
        pojo.setCode("code");
        pojo.setName("name");

        String json = this.g.toJson(pojo);

        this.mockMvc.perform(post("/courses").content(json))
                .andDo(print())
                .andExpect(status().isCreated());


    }

    @Test
    void createCourseError() throws Exception {

        when(this.courseService.saveCourse(any(CoursePojo.class))).thenReturn(false);

        CoursePojo pojo = new CoursePojo();
        pojo.setName("name");

        String json = this.g.toJson(pojo);

        this.mockMvc.perform(post("/courses").content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }


    @Test
    void updateCourse() throws Exception {

        CoursePojo pojo = new CoursePojo();
        pojo.setCode("code");
        pojo.setName("name_updated");


        when(this.courseService.updateCourse(anyInt(), any(CoursePojo.class))).thenReturn(true);

        this.mockMvc.perform(put("/courses/1").content(this.g.toJson(pojo)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void updateCourseError() throws Exception {

        CoursePojo pojo = new CoursePojo();
        pojo.setCode("code");
        pojo.setName("name_updated");


        when(this.courseService.updateCourse(anyInt(), any(CoursePojo.class))).thenReturn(false);

        this.mockMvc.perform(put("/courses/1").content(this.g.toJson(pojo)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteCourse() throws Exception {


        when(this.courseService.deleteCourse(anyInt())).thenReturn(true);

        this.mockMvc.perform(delete("/courses/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourseError() throws Exception {


        when(this.courseService.deleteCourse(anyInt())).thenReturn(false);

        this.mockMvc.perform(delete("/courses/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}