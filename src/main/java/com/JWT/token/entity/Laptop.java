package com.JWT.token.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Laptop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;


    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore // Prevents infinite JSON loop back to Employee
    private Employee employee;
}
