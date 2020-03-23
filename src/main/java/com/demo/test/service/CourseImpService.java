package com.demo.test.service;

import com.demo.test.models.ApiEntity;
import com.demo.test.models.Course;
import com.demo.test.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.print.attribute.standard.PageRanges;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Felipe González Alfaro on 20-03-20.
 */
@Service
@Qualifier("courseservice")
public class CourseImpService implements StandarService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<? extends ApiEntity> getPag(int page, int size) {

        Pageable page_req = PageRequest.of(page, size);
        Page<Course> resultPage = this.courseRepository.findAll(page_req);

        return page > resultPage.getTotalPages() ? null : resultPage;

    }

    @Override
    public List<? extends ApiEntity> getAll() {

        return this.courseRepository.findAll();
    }

    @Override
    public boolean save(ApiEntity entity) {

        Course course = this.courseRepository.save((Course) entity);

        return course.getId() > 0;
    }


    @Override
    public ApiEntity getById(int id) {

        Optional<Course> resp = this.courseRepository.findById(id);


        return resp.orElse(null);
    }

    @Override
    public boolean update(int id, ApiEntity entity) {

        Optional<Course> course1 = this.courseRepository.findById(id);


        if (course1.isPresent()) {
            Course course = course1.get();
            Course course_param = (Course) entity;
            String name = course_param.getName() != null ? course_param.getName() : course.getName();
            String code = course_param.getCode() != null ? course_param.getCode() : course.getCode();
            course.setName(name);
            course.setCode(code);

            this.courseRepository.save(course);
            return true;
        } else {

            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        Optional<Course> c = this.courseRepository.findById(id);

        if (c.isPresent()) {
            this.courseRepository.delete(c.get());
            return true;
        } else {
            return false;
        }

    }
}
