package com.demo.test.controllers;

import com.demo.test.models.ApiEntity;
import com.demo.test.models.Course;
import com.demo.test.service.StandarService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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
@CrossOrigin
@Api(value = "Courses", description = "Course controller")
public class CourseController extends HandlerArgumentController {

    @Autowired
    @Qualifier("courseservice")
    private StandarService coursesService;

    @Autowired
    private Gson gson;


    /**
     * Method that delivers a list of results page (@{@link Page})
     *
     * @param auth Jwt Token
     * @param page Number of page
     * @param size Size of the page
     * @return Page of {@link Course}
     */
    @ApiParam
    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<? extends ApiEntity> getCursesPagination(@ApiParam(defaultValue = "Bearer ") String auth, @ApiParam(name = "") @RequestParam int page, @RequestParam int size) {

        return this.coursesService.getPag(page, size);

    }

    /**
     * Method that deliver all courses in one array.
     *
     * @param auth Jwt Token
     * @return Array of @{@link Course}
     */
    @GetMapping(value = "/courses/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends ApiEntity>> getAll(@ApiParam(defaultValue = "Bearer ") String auth) {

        List<? extends ApiEntity> resp = this.coursesService.getAll();

        if (resp.size() > 0) {
            return ResponseEntity.ok()
                    .body(resp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Method to obtains specific course by id
     *
     * @param auth Jwt Token
     * @param id   id of the course
     * @return @{@link Course}
     */
    @GetMapping(value = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends ApiEntity> getCourse(@ApiParam(defaultValue = "Bearer ") String auth, @PathVariable int id) {

        Course resp = (Course) this.coursesService.getById(id);

        if (resp != null) {
            return ResponseEntity.ok().body(resp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "/courses", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCourse(@ApiParam(defaultValue = "Bearer ") String auth, @Valid @RequestBody Course course) {

        boolean resp = this.coursesService.save(course);

        HttpStatus status = resp ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(status);
    }

    @PutMapping(value = "/courses/{id}", headers = "Accept=application/json")
    public ResponseEntity<Object> updateCourse(@ApiParam(defaultValue = "Bearer ") String auth, @PathVariable int id, @Valid @RequestBody Course course) {


        boolean resp = this.coursesService.update(id, course);

        HttpStatus status = resp ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(status);
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<Object> deleteCourse(@ApiParam(defaultValue = "Bearer ") String auth, @PathVariable int id) {
        boolean resp = this.coursesService.delete(id);

        HttpStatus status = resp ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }


}
