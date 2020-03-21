package com.demo.test.service;

import com.demo.test.models.Course;
import com.demo.test.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pojo.CoursePojo;

import javax.print.attribute.standard.PageRanges;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Felipe González Alfaro on 20-03-20.
 */
@Service
public class CourseImpService implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> getCoursesPag(int page, int size) {

        Pageable page_req = PageRequest.of(page, size);
        Page<Course> resultPage = this.courseRepository.findAll(page_req);

        return page > resultPage.getTotalPages() ? null : resultPage;

    }

    @Override
    public List<Course> getCourses() {

        return this.courseRepository.findAll();
    }

    @Override
    public boolean saveCourse(CoursePojo course) {
        Course course1 = Course.builder()
                .id(0)
                .code(course.getCode())
                .name(course.getName())
                .build();

        course1 = this.courseRepository.save(course1);

        return course1.getId() > 0;
    }


    @Override
    public Course getCourseById(int id) {

        Optional<Course> resp = this.courseRepository.findById(id);


        return resp.orElse(null);
    }

    @Override
    public boolean updateCourse(int id, CoursePojo course) {

        Optional<Course> course1 = this.courseRepository.findById(id);

        if (course1.isPresent()) {
            Course c = course1.get();
            c.setName(course.getName());
            c.setCode(course.getName());

            this.courseRepository.save(c);
            return true;
        } else {

            return false;
        }
    }

    @Override
    public boolean deleteCourse(int id) {

        Optional<Course> c = this.courseRepository.findById(id);

        if (c.isPresent()) {
            this.courseRepository.delete(c.get());
            return true;
        } else {
            return false;
        }

    }
}
