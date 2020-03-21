package com.demo.test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotEmpty(message = "Please provide a name")
    private String name;


    @NotEmpty(message = "Please provide a code")
    @Column(length = 4)
    private String code;
}
