package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.EmployeeSkille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {
    Optional<EmployeeSkill> findBySkill(EmployeeSkille skill);
}
