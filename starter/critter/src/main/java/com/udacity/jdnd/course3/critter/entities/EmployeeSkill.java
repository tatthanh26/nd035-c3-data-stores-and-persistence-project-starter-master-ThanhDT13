package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkille;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EmployeeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeSkille skill;
}
