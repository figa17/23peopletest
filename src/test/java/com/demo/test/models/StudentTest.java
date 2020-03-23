package com.demo.test.models;

import com.demo.test.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class StudentTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void createStudent() {

        Course course = Course.builder()
                .name("cuso_1")
                .code("code")
                .build();

        Student student = Student.builder()
                .rut("1111111-k")
                .name("name")
                .lastName("lastName")
                .age(19)
                .course(course)
                .build();

        studentRepository.save(student);


        Optional<Student> res = studentRepository.findById(student.getId());

        assertTrue(res.isPresent());
        assertEquals(res.get().getName(), student.getName());
        assertEquals(res.get().getLastName(), student.getLastName());
        assertEquals(res.get().getAge(), student.getAge());
        assertEquals(res.get().getRut(), student.getRut());
    }


    @Test
    public void updateStudent() {
        Course course = Course.builder()
                .name("cuso_1")
                .code("code")
                .build();

        Student student = Student.builder()
                .rut("1111111-k")
                .name("name")
                .lastName("lastName")
                .age(19)
                .course(course)
                .build();

        studentRepository.save(student);


        Optional<Student> res = studentRepository.findById(student.getId());

        Student updateStudent = res.orElse(null);

        updateStudent.setName("updateName");
        updateStudent.setLastName("update lastName");

        studentRepository.save(student);

        Optional<Student> res_update = studentRepository.findById(student.getId());

        assertTrue(res_update.isPresent());
        assertEquals(res_update.get().getName(), updateStudent.getName());
        assertEquals(res_update.get().getLastName(), updateStudent.getLastName());
        assertEquals(res_update.get().getAge(), updateStudent.getAge());
        assertEquals(res_update.get().getRut(), updateStudent.getRut());
    }


}