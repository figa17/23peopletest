package com.demo.test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by Felipe González Alfaro on 19-03-20.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Course extends ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank(message = "Please provide a name")
    @NotNull
    private String name;


    @NotBlank(message = "Please provide a code")
    @Column(length = 4)
    @Size(min = 1, max = 4, message = "Code had max 4 chars")
    private String code;
}
