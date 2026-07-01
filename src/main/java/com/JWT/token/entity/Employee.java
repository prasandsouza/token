package com.JWT.token.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "employees")
@Data
public class Employee extends User {

    private String name;

    // 1. One-to-One Bidirectional Mapping
    // CascadeType.ALL means if we save/delete Employee, the Laptop is automatically saved/deleted
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Laptop laptop;

    // 2. Many-to-One (The "Owning" side of the relationship holding the foreign key)
    // FetchType.LAZY prevents loading the whole department unless explicitly called
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // 3. Many-to-Many Relationship
    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects = new ArrayList<>();

}
