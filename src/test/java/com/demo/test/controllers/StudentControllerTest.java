package com.demo.test.controllers;

import com.demo.test.models.Course;
import com.demo.test.models.Student;
import com.demo.test.service.StudentService;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private Gson gson;


    @Test
    void getStudentPagination() throws Exception {
        Faker faker = new Faker();

        Student student;
        int student_size = 5;
        List<Student> studentList = new ArrayList<>();

        Course course = Course.builder()
                .id(1)
                .code(faker.code().ean8().substring(0, 4))
                .name(faker.name().name())
                .build();

        for (int i = 0; i < student_size; i++) {

            student = Student.builder()
                    .id(i)
                    .rut(faker.code().ean8().substring(0, 4))
                    .name(faker.name().name())
                    .lastName(faker.name().lastName())
                    .age((int) faker.number().randomNumber(2, true))
                    .course(course)
                    .build();
            studentList.add(student);
        }


        Page<Student> studentPage = new PageImpl<>(studentList, PageRequest.of(1, student_size), 5);

        when(this.studentService.getStudentPag(anyInt(), anyInt())).thenReturn(studentPage);

        this.mockMvc.perform(get("/student")
                .param("page", "1").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("size", is(5)))
                .andExpect(jsonPath("number", is(1)))
                .andExpect(jsonPath("totalPages", is(2)));
    }

    @Test
    void getAll() throws Exception {
        Course course = Course.builder()
                .id(1)
                .code("code")
                .name("course_1")
                .build();

        Student student = Student.builder()
                .id(1)
                .rut("rut")
                .name("name")
                .lastName("last_name")
                .age(19)
                .course(course)
                .build();

        when(this.studentService.getStudents()).thenReturn(Collections.singletonList(student));

        this.mockMvc.perform(get("/student/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].lastName", is("last_name")))
                .andExpect(jsonPath("$[0].course.name", is("course_1")));
    }

    @Test
    public void getAllEmptyError() throws Exception {

        when(this.studentService.getStudents()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/student/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStudent() throws Exception {
        Course course = Course.builder()
                .code("code")
                .name("course_1")
                .id(1)
                .build();

        Student student = Student.builder()
                .id(1)
                .rut("rut")
                .name("name")
                .lastName("last_name")
                .age(19)
                .course(course)
                .build();


        when(this.studentService.getStudentById(anyInt())).thenReturn(student);


        this.mockMvc.perform(get("/student/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("name")))
                .andExpect(jsonPath("lastName", is("last_name")))
                .andExpect(jsonPath("course.name", is("course_1")));
    }

    @Test
    void getStudentotFound() throws Exception {


        when(this.studentService.getStudentById(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/student/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createStudent() throws Exception {
        Course course = Course.builder()
                .code("code")
                .name("course_1")
                .id(1)
                .build();

        Student student = Student.builder()
                .id(1)
                .rut("rut")
                .name("name")
                .lastName("last_name")
                .age(19)
                .course(course)
                .build();

        when(this.studentService.saveStudent(any(Student.class))).thenReturn(true);


        this.mockMvc.perform(post("/student").content(this.gson.toJson(student)))
                .andDo(print())
                .andExpect(status().isCreated());

    }


    @Test
    void createStudentError() throws Exception {

        when(this.studentService.saveStudent(any(Student.class))).thenReturn(false);


        this.mockMvc.perform(post("/student").content(this.gson.toJson(new Student())))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    @Test
    void updateStudent() throws Exception {

        when(this.studentService.updateStudent(anyInt(), any(Student.class))).thenReturn(true);

        this.mockMvc.perform(put("/student/1").content(this.gson.toJson(new Student())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateStudentError() throws Exception {


        when(this.studentService.updateStudent(anyInt(), any(Student.class))).thenReturn(false);

        this.mockMvc.perform(put("/student/1").content(this.gson.toJson(new Student())))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteStudent() throws Exception {
        when(this.studentService.deleteStudent(anyInt())).thenReturn(true);

        this.mockMvc.perform(delete("/student/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentError() throws Exception {


        when(this.studentService.deleteStudent(anyInt())).thenReturn(false);

        this.mockMvc.perform(delete("/student/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}