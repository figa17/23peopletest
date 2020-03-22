package com.demo.test.controllers;

import com.demo.test.models.Student;
import com.demo.test.service.StudentService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@RestController
@Api(value = "Courses", description = "Student controller")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private Gson gson;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Student> getStudentPagination(@RequestParam int page, @RequestParam int size) {

        return this.studentService.getStudentPag(page, size);
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
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Student resp = this.studentService.getStudentById(id);

        if (resp != null) {
            return ResponseEntity.ok().body(resp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/student")
    public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student) {


        boolean resp = this.studentService.saveStudent(student);

        HttpStatus status = resp ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(status);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {

        boolean resp = this.studentService.updateStudent(id, student);

        HttpStatus status = resp ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {

        boolean resp = this.studentService.deleteStudent(id);

        HttpStatus status = resp ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }
}
