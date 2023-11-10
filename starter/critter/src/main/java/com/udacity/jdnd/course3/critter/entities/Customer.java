package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.Entity;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(targetEntity = Pet.class,mappedBy="customer", cascade = CascadeType.ALL)
    private List<Pet> pets;
}
