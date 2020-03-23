package com.demo.test.service;

import com.demo.test.models.ApiEntity;
import com.demo.test.models.Course;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
public interface StandarService {

    Page<? extends ApiEntity> getPag(int page, int size);

    List<? extends ApiEntity> getAll();

    boolean save(ApiEntity entity);

    ApiEntity getById(int id);

    boolean update(int id, ApiEntity entity);

    boolean delete(int id);
}
