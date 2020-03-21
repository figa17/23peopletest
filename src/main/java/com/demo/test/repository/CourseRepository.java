package com.demo.test.repository;

import com.demo.test.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
