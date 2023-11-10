package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.*;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.*;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, CustomerRepository customerRepository, EmployeeSkillRepository employeeSkillRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
        this.employeeSkillRepository = employeeSkillRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = setToScheduleEntity(scheduleDTO);
        return setToScheduleDTO(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = (List<Schedule>) scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        if(scheduleList.isEmpty()) {
            return scheduleDTOList;
        }

        scheduleDTOList = scheduleList.stream().map(this::setToScheduleDTO).collect(Collectors.toList());
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleByPetId(long petId) {

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find the pet ID: " + petId));

        List<Schedule> scheduleList = scheduleRepository.findByPets(pet);
        if(scheduleList.isEmpty()) {
            return null;
        }

        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(this::setToScheduleDTO).collect(Collectors.toList());

        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleByEmployeeId(long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find the employee ID: " + employeeId));

        List<Schedule> scheduleList = scheduleRepository.findByEmployees(employee);
        if(scheduleList.isEmpty()) {

            return null;
        }

        return scheduleList.stream().map(this::setToScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleByCustomerId(long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find the customer ID: " + customerId));
        List<Pet> petList = customer.getPets();

        List<Schedule> scheduleList = new ArrayList<>();
        for(Pet pet: petList){
            scheduleList.addAll(scheduleRepository.findByPets(pet));
        }
        if(scheduleList.isEmpty()) {

            return null;
        }

        return scheduleList.stream().map(this::setToScheduleDTO).collect(Collectors.toList());
    }

    public Schedule setToScheduleEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        schedule.setDate(scheduleDTO.getDate());

        if (scheduleDTO.getEmployeeIds() != null) {
            List<Employee> employeeList = scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toList());
            schedule.setEmployees(employeeList);
        }

        if (scheduleDTO.getPetIds() != null) {
            List<Pet> petList = scheduleDTO.getPetIds().stream().map(id -> petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toList());
            schedule.setPets(petList);
        }

        if (scheduleDTO.getActivities() != null) {
            Set<EmployeeSkill> employeeSkillList = scheduleDTO.getActivities().stream().map(employeeSkill -> employeeSkillRepository.findBySkill(employeeSkill).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toSet());
            schedule.setActivities(employeeSkillList);
        }

        return schedule;
    }

    public ScheduleDTO setToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(e->e.getId()).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(p->p.getId()).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities().stream().map(a->a.getSkill()).collect(Collectors.toSet()));

        return scheduleDTO;
    }
}
