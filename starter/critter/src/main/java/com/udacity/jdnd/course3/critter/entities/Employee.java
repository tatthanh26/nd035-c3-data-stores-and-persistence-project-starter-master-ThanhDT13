package com.udacity.jdnd.course3.critter.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_employee_skill", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "employee_skill_id"))
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_day", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "day_id"))
    private Set<Day> daysAvailable = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules;
}
