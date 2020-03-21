package com.demo.test.service;

import com.demo.test.models.Course;
import com.demo.test.models.Student;
import org.springframework.data.domain.Page;
import pojo.CoursePojo;
import pojo.StudentPojo;

import java.util.List;

/**
 * Created by Felipe González Alfaro on 21-03-20.
 */
public interface StudentService {

    Page<Student> getStudentPag(int page, int size);

    List<Student> getStudents();

    boolean saveStudent(StudentPojo course);

    Student getStudentById(int id);

    boolean updateStudent(int id, StudentPojo course);

    boolean deleteStudent(int id);
}
