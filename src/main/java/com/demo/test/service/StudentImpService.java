package com.demo.test.service;

import com.demo.test.models.Course;
import com.demo.test.models.Student;
import com.demo.test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Felipe González Alfaro on 21-03-20.
 */
@Service
public class StudentImpService implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<Student> getStudentPag(int page, int size) {
        System.out.println(page);
        System.out.println(size);
        Pageable page_req = PageRequest.of(page, size);
        Page<Student> resultPage = this.studentRepository.findAll(page_req);

        return page > resultPage.getTotalPages() ? null : resultPage;
    }

    @Override
    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    @Override
    public boolean saveStudent(Student student) {

        student = this.studentRepository.save(student);

        return student.getId() > 0;
    }

    /**
     * Get Student by specific id.
     *
     * @param id Student Id
     * @return Student
     */
    @Override
    public Student getStudentById(int id) {
        Optional<Student> resp = this.studentRepository.findById(id);


        return resp.orElse(null);
    }

    @Override
    public boolean updateStudent(int id, Student student) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student1 = optionalStudent.get();
            student.setName(student.getName());
            student.setLastName(student.getName());
            student.setRut(student.getRut());
            student.setAge(student.getAge());
            student.setCourse(student.getCourse());

            this.studentRepository.save(student1);
            return true;
        } else {

            return false;
        }
    }

    /**
     * @param id Student dd.
     * @return Is deleted.
     */
    @Override
    public boolean deleteStudent(int id) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            this.studentRepository.delete(optionalStudent.get());
            return true;
        } else {
            return false;
        }
    }
}
