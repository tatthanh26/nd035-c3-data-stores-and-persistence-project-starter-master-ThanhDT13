package com.udacity.jdnd.course3.critter.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_employee", joinColumns = {@JoinColumn(name = "schedule_id")}, inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "schedule_pet",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets = new ArrayList<>();

    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_employee_skill", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "employee_skill_id"))
    private Set<EmployeeSkill> activities = new HashSet<>();
}
