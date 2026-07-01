package com.JWT.token.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Data
public class Department {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "department")
    @JsonIgnore // Prevents infinite loop back to Employee list when serializing Department
    private List<Employee> employees;
}
