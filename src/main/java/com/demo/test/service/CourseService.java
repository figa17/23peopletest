package com.demo.test.service;

import com.demo.test.models.Course;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Felipe González Alfaro on 20-03-20.
 */
public interface CourseService {

    Page<Course> getCoursesPag(int page, int size);

    List<Course> getCourses();

    boolean saveCourse(Course course);

    Course getCourseById(int id);

    boolean updateCourse(int id, Course course);

    boolean deleteCourse(int id);
}
