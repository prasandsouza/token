package com.JWT.token.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
// JOINED strategy creates separate tables for User and Employee, linked by ID
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

}
