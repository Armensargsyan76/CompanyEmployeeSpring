package com.example.companyemployeespring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private double salary;
    @Enumerated(value = EnumType.STRING)
    private Position position;
    @ManyToOne
    private Company company;
    @Column(name = "profile_pic")
    private String profilePic;

}
