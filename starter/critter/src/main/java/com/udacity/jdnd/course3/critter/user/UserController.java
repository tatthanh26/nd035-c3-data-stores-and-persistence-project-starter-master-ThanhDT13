package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            return customerService.save(customerDTO);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        try {
            return customerService.getAllCustomers();
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        try {
            return customerService.getCustomerByPetId(petId);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            return employeeService.save(employeeDTO);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try {
            return employeeService.getById(employeeId);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeService.setAvailability(employeeId, daysAvailable);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        try {
            return employeeService.findEmployeesForService(employeeDTO);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

}
