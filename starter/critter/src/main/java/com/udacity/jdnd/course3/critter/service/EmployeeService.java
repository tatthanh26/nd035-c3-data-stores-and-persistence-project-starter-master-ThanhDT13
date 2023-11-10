package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Day;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.DayRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkille;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final DayRepository dayRepository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeSkillRepository employeeSkillRepository, DayRepository dayRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeSkillRepository = employeeSkillRepository;
        this.dayRepository = dayRepository;
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = setToEmployeeEntity(employeeDTO);
        return setToEmployeeDTO(employeeRepository.save(employee));
    }

    public EmployeeDTO getById(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can not find the employee Id: " + id));
        return this.setToEmployeeDTO(employee);
    }

    public void setAvailability(long id, Set<DayOfWeek> daysAvailable) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can not find the employee Id: " + id));
        Set<Day> dayAvailableSet = new HashSet<>();
        for (DayOfWeek day:daysAvailable) {

            Day dayOfWeeKSet = dayRepository.findDayByDayOfWeek(day)
                    .orElseThrow( () -> new EntityNotFoundException("Can not find the Day: "+ day.toString()));

            dayAvailableSet.add(dayOfWeeKSet);
        }
        employee.setDaysAvailable(dayAvailableSet);
        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        Day day = dayRepository.findDayByDayOfWeek(employeeDTO.getDate().getDayOfWeek())
                .orElseThrow(()->new EntityNotFoundException("Can not find the day"));
        List<Employee> employees = employeeRepository.findByDaysAvailable(day);
        Set<EmployeeSkill> employeeSkillSet = new HashSet<>();
        for (EmployeeSkille employeeSkille:employeeDTO.getSkills()) {
            EmployeeSkill employeeSkill = employeeSkillRepository.findBySkill(employeeSkille).orElseThrow(() -> new EntityNotFoundException("Can not find the skill"));
            employeeSkillSet.add(employeeSkill);
        }
        List<Employee> availableEmployees = new ArrayList<>();
        for(Employee employee : employees){
            if(employee.getSkills().containsAll(employeeSkillSet)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees.stream().map(this::setToEmployeeDTO).collect(Collectors.toList());
    }

    private Employee setToEmployeeEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        if (employeeDTO.getSkills() != null) {
            Set<EmployeeSkill> employeeSkillEntitySet = employeeDTO.getSkills().stream().map(employeeSkill -> employeeSkillRepository.findBySkill(employeeSkill).orElseThrow(() -> new EntityNotFoundException("Can not found employee"))).collect(Collectors.toSet());
            employee.setSkills(employeeSkillEntitySet);
        }

        if (employeeDTO.getDaysAvailable() != null) {
            Set<Day> dayOfWeekEntitySet = employeeDTO.getDaysAvailable().stream().map(day -> dayRepository.findDayByDayOfWeek(day).orElseThrow(() -> new EntityNotFoundException("Can not found day"))).collect(Collectors.toSet());
            employee.setDaysAvailable(dayOfWeekEntitySet);
        }

        return employee;
    }

    private EmployeeDTO setToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills().stream().map(employeeSkillEntity -> employeeSkillEntity.getSkill()).collect(Collectors.toSet()));
        employeeDTO.setDaysAvailable(employee.getDaysAvailable().stream().map(dayOfWeekEntity -> dayOfWeekEntity.getDayOfWeek()).collect(Collectors.toSet()));

        return employeeDTO;
    }
}
