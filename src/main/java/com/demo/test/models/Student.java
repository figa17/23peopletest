package com.demo.test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Please provide a rut")
    private String rut;
    @NotEmpty(message = "Please provide a name")
    private String name;
    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @NotEmpty(message = "Please provide age")
    @Min(value = 18)
    private int age;

    @NotEmpty(message = "Please provide a course")
    @ManyToOne
    private Course course;
}
