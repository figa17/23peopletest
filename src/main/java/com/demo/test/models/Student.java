package com.demo.test.models;

import com.demo.test.models.validator.RutConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Student extends ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Please provide a rut")
    @NotNull
    @RutConstraint
    private String rut;

    @NotBlank(message = "Please provide a name")
    @NotNull
    private String name;

    @NotBlank(message = "Please provide a last name")
    @NotNull
    private String lastName;

    @Min(value = 18)
    private int age;

    @ManyToOne
    private Course course;
}
