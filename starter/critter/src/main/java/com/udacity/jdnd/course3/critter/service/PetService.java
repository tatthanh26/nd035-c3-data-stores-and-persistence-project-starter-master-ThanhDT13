package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = setToPetEntity(petDTO);
        return setToPetDTO(petRepository.save(pet));
    }

    public PetDTO getPetById(long petId) {
        return setToPetDTO(petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Not found pet id: " + petId)));
    }

    public List<PetDTO> getAllPet() {
        List<Pet> listPet = (List<Pet>) petRepository.findAll();

        return listPet.stream().map(this::setToPetDTO).collect(Collectors.toList());
    }

    public List<PetDTO> getPetsByOwnerId(long ownerId) {
        List<Pet> petList = petRepository.findByCustomerId(ownerId);

        return petList.stream().map(this::setToPetDTO).collect(Collectors.toList());
    }

    public Pet setToPetEntity(PetDTO petDTO) {
        Customer customer = customerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException(("Can not find customer id: " + petDTO.getOwnerId())));

        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setCustomer(customer);
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        return pet;
    }

    public PetDTO setToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());

        return petDTO;
    }
}
