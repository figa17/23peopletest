package com.demo.test.controllers;

import com.demo.test.models.Course;
import com.demo.test.models.Student;
import com.demo.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudentPagination() {

        return Collections.emptyList();
    }

    @GetMapping(value = "/student/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getAll() {

        List<Student> resp = this.studentService.getStudents();

        if (resp.size() > 0) {
            return ResponseEntity.ok()
                    .body(resp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getStudent(@PathVariable int id) {

        return null;
    }

    @PostMapping("/student")
    public int createStudent(@RequestBody String json) {

        return 0;
    }

    @PutMapping("/student/{id}")
    public int updateStudent(@PathVariable int id, @RequestBody String json) {

        return 0;
    }

    @DeleteMapping("/student/{id}")
    public int deleteStudent(@PathVariable int id) {
        return 0;
    }
}
