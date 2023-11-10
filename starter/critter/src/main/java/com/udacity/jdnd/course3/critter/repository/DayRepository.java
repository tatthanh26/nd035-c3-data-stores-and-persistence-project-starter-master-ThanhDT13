package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findDayByDayOfWeek(DayOfWeek day);
}
