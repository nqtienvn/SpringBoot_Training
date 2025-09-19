package com.tien.springboot_traning.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
     int id;
    @Column(name = "Name")
     String name;
    @Column(name = "Age")
     int age;
    @Column(name = "Password")
    String password;
    @ManyToMany
    Set<Role> roles;
}
