package ro.ubbcluj.maserver.controllers;


import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;
import ro.ubbcluj.maserver.model.Dog;
import ro.ubbcluj.maserver.repository.DogRepository;

import java.util.List;

@RestController
public class DogController {

    private final DogRepository dogRepository;

    public DogController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/dogs")
    public Dog newDog(@RequestBody Dog newDog) {
        return dogRepository.save(newDog);
    }

    // Single item

    @GetMapping("/dogs/{id}")
    public Dog dogDetails(@PathVariable Long id) throws NotFoundException {

        return dogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dog not found"));
    }

    @PutMapping("/dogs")
    public Dog replaceDog(@RequestBody Dog newDog) {

         Dog oldDog = dogRepository.findById(newDog.getID()).orElse(null);
         if(oldDog == null)
             return null;
         return dogRepository.save(newDog);
    }

    @DeleteMapping("/dogs/{id}")
    public void deleteDog(@PathVariable Long id) {
        dogRepository.deleteById(id);
    }
}
