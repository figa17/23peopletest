package com.demo.test.service;

import com.demo.test.models.ApiEntity;
import com.demo.test.models.Course;
import com.demo.test.models.Student;
import com.demo.test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Qualifier("studentservice")
public class StudentImpService implements StandarService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<? extends ApiEntity> getPag(int page, int size) {

        Pageable page_req = PageRequest.of(page, size);
        Page<Student> resultPage = this.studentRepository.findAll(page_req);

        return page > resultPage.getTotalPages() ? null : resultPage;
    }

    @Override
    public List<? extends ApiEntity> getAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public boolean save(ApiEntity entity) {

        Student student = this.studentRepository.saveAndFlush((Student) entity);
        return student.getId() > 0;
    }

    /**
     * Get Student by specific id.
     *
     * @param id Student Id
     * @return Student
     */
    @Override
    public ApiEntity getById(int id) {
        Optional<Student> resp = this.studentRepository.findById(id);


        return resp.orElse(null);
    }

    @Override
    public boolean update(int id, ApiEntity entity) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student_param = (Student) entity;

            Student student = optionalStudent.get();

            student.setName(student_param.getName());
            student.setLastName(student_param.getLastName());
            student.setRut(student_param.getRut());
            student.setAge(student_param.getAge());
            student.setCourse(student_param.getCourse());

            this.studentRepository.saveAndFlush(student);
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
    public boolean delete(int id) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            this.studentRepository.delete(optionalStudent.get());
            return true;
        } else {
            return false;
        }
    }
}
