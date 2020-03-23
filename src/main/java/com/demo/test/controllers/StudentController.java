package com.demo.test.controllers;

import com.demo.test.models.ApiEntity;
import com.demo.test.models.Student;
import com.demo.test.service.StandarService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class StudentController extends HandlerArgumentController {

    @Autowired
    @Qualifier("studentservice")
    private StandarService studentService;

    @Autowired
    private Gson gson;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<? extends ApiEntity> getStudentPagination(@RequestParam int page, @RequestParam int size) {

        return this.studentService.getPag(page, size);
    }

    @GetMapping(value = "/student/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends ApiEntity>> getAll() {

        List<? extends ApiEntity> resp = this.studentService.getAll();

        if (resp.size() > 0) {
            return ResponseEntity.ok()
                    .body(resp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends ApiEntity> getStudent(@PathVariable int id) {

        Student resp = (Student) this.studentService.getById(id);

        if (resp != null) {
            return ResponseEntity.ok().body(resp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/student")
    public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student) {

        boolean resp = this.studentService.save(student);

        HttpStatus status = resp ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(status);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {

        boolean resp = this.studentService.update(id, student);

        HttpStatus status = resp ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {

        boolean resp = this.studentService.delete(id);

        HttpStatus status = resp ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

}
