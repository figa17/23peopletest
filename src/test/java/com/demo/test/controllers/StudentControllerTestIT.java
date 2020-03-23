package com.demo.test.controllers;

import com.demo.test.models.Course;
import com.demo.test.models.Student;
import com.demo.test.service.StandarService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureTestDatabase
public class StudentControllerTestIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Qualifier("courseservice")
    private StandarService courseService;

    @Autowired
    @Qualifier("studentservice")
    private StandarService studentService;

    @Autowired
    private Gson gson;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        int student_size = 22;
        Course course;
        Student student;

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


        for (int i = 1; i <= student_size; i++) {
            student = Student.builder()
                    .id(i)
                    .rut("16660706-k")
                    .name("name_" + i)
                    .lastName("last_name_" + i)
                    .age(19 + i)
                    .course(course)
                    .build();
            this.studentService.save(student);
        }
    }


    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(wac.getBean("studentController"));
    }


    @Test
    public void getAll() throws Exception {

        this.mockMvc.perform(get("/student/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(22)));
    }


    @Test
    public void getAllPag() throws Exception {
        this.mockMvc.perform(get("/student").param("page", "1").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("size", is(5)))
                .andExpect(jsonPath("number", is(1)));

    }


    @Test
    public void getStudent() throws Exception {

        this.mockMvc.perform(get("/student/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name_5")))
                .andExpect(jsonPath("$.lastName", is("last_name_5")))
                .andExpect(jsonPath("$.id", is(5)));

    }

    @Test
    public void getStudentNotFound() throws Exception {

        this.mockMvc.perform(get("/student/999999"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createStudent() throws Exception {

        Course course = Course.builder()
                .id(2)
                .code("0002")
                .name("course_2")
                .build();

        Student student = Student.builder()
                .id(23)
                .rut("16660706-k")
                .name("name_" + 23)
                .lastName("last_name_" + 23)
                .age(19 + 23)
                .course(course)
                .build();


        this.mockMvc.perform(post("/student")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(student)))
                .andDo(print())
                .andExpect(status().isCreated());


        this.mockMvc.perform(get("/student/23"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name_23")))
                .andExpect(jsonPath("$.lastName", is("last_name_23")))
                .andExpect(jsonPath("$.id", is(23)));

    }

    @Test
    public void createStudentErrorContentType() throws Exception {

        Course course = Course.builder()
                .id(22)
                .code("0022")
                .name("course_22")
                .build();

        Student student = Student.builder()
                .id(22)
                .rut("16660706-k")
                .name("name_" + 22)
                .lastName("last_name_" + 22)
                .age(19 + 22)
                .course(course)
                .build();


        this.mockMvc.perform(post("/student")
                .content(this.gson.toJson(student)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void createStudentErrorStudentInvalidRut() throws Exception {

        Course course = Course.builder()
                .id(22)
                .code("0022")
                .name("course_22")
                .build();

        Student student = Student.builder()
                .id(22)
                .rut("asdasd") // invalid rut
                .name("name_" + 22)
                .lastName("last_name_" + 22)
                .age(19 + 22)
                .course(course)
                .build();


        this.mockMvc.perform(post("/student")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(student)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("errors[0]", is("Invalid Rut")));
    }


    @Test
    public void updateCourse() throws Exception {

        Course course = Course.builder()
                .id(2)
                .code("0002")
                .name("course_2")
                .build();

        Student student = Student.builder()
                .id(22)
                .rut("16660706-k")
                .name("name_updated_" + 22)
                .lastName("last_name_updated_" + 22)
                .age(19 + 22)
                .course(course)
                .build();


        this.mockMvc.perform(put("/student/22")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(student)))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/student/22"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name_updated_22")))
                .andExpect(jsonPath("$.lastName", is("last_name_updated_22")));

    }


    @Test
    public void updateCourseError() throws Exception {

        Course course = Course.builder()
                .id(2)
                .code("0002")
                .name("course_2")
                .build();

        Student student = Student.builder()
                .id(22)
                .rut("16660706-k")
                .name("name_updated_" + 22)
                .lastName("last_name_updated_" + 22)
                .age(19 + 22)
                .course(course)
                .build();


        this.mockMvc.perform(put("/student/9999")
                .header("Content-Type", "application/json")
                .content(this.gson.toJson(student)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    public void deleteCourse() throws Exception {


        this.mockMvc.perform(delete("/student/3")
                .header("Content-Type", "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCourseError() throws Exception {


        this.mockMvc.perform(delete("/student/9999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
