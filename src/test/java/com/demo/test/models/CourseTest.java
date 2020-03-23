package com.demo.test.models;

import com.demo.test.repository.CourseRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class CourseTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CourseRepository courseRepository;


    @Test
    public void createCourse() {

        Course course = Course.builder()
                .code("code")
                .name("name")
                .build();

        testEntityManager.persist(course);

        List<Course> res = courseRepository.findAll();

        assertTrue(res.size() > 0);
        assertEquals(res.get(0).getId(), course.getId());
        assertEquals(res.get(0).getName(), course.getName());
        assertEquals(res.get(0).getCode(), course.getCode());
        testEntityManager.flush();
    }


    @Test
    public void updateCourse() {

        Course course = Course.builder()
                .code("code")
                .name("name")
                .build();

        course = testEntityManager.persist(course);
        List<Course> res = courseRepository.findAll();
        Course update = res.get(0);

        update.setName("update");
        update.setCode("1123");

        courseRepository.save(update);

        Optional<Course> res_update = courseRepository.findById(course.getId());

        assertTrue(res_update.isPresent());
        assertEquals(res_update.get().getId(), update.getId());
        assertEquals(res_update.get().getName(), update.getName());
        assertEquals(res_update.get().getCode(), update.getCode());

    }
}