package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            return scheduleService.createSchedule(scheduleDTO);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        try {
            return scheduleService.getAllSchedules();
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        try {
            return scheduleService.getScheduleByPetId(petId);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        try {
            return scheduleService.getScheduleByEmployeeId(employeeId);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        try {
            return scheduleService.getScheduleByCustomerId(customerId);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }
}
