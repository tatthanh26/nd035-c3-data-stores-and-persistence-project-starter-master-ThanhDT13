package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    private LocalDate birthDate;

    private String notes;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> schedules = new ArrayList<>();;
}
