package pojo;

import com.demo.test.models.Course;
import lombok.Data;

import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Felipe González Alfaro on 20-03-20.
 */
@Data
public class StudentPojo {

    private int id;
    private String rut;
    private String name;
    private String lastName;
    private int age;
    private Course course;
}
