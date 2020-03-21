package pojo;

import com.demo.test.models.Course;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Felipe González Alfaro on 21-03-20.
 */
@Data
@Builder
public class PageResult {

    private int page;
    private int totalPages;
    private List<Course> courseList;
}
