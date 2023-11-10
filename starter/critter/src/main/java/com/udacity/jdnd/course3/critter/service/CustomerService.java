package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = setToCustomerEntity(customerDTO);
        return setToCustomerDTO(customerRepository.save(customer));
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(customer -> setToCustomerDTO(customer)).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerByPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Can not find the pet Id: " + petId));
        return setToCustomerDTO(pet.getCustomer());
    }

    private Customer setToCustomerEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        return customer;
    }

    private CustomerDTO setToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        if (customer.getPets() != null) {
            List<Long> longList = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            customerDTO.setPetIds(longList);
        }
        return customerDTO;
    }
}
