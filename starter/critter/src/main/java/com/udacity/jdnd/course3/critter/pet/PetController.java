package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        try {
            return petService.savePet(petDTO);
        }catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return petService.getPetById(petId);
        }catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        try {
            return petService.getAllPet();
        }catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        try {
            return petService.getPetsByOwnerId(ownerId);
        }catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }
}
