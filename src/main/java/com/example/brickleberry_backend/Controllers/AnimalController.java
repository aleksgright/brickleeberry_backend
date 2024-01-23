package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Entities.Animal;
import com.example.brickleberry_backend.Repositories.AnimalRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/animal")
public class AnimalController {
    private final AnimalRepository animalRepository;

    @GetMapping("/all")
    public List<Animal> getAllAnimalsPage(
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam() int pageNumber) {
        return animalRepository.getAllAnimalsPaged(pageSize, pageNumber * pageSize);
    }


    @GetMapping("/animalsByTerritory")
    public List<Animal> getAnimalsByTerritoryPage(
            @RequestParam int territoryId,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam int pageNumber) {
        return animalRepository.getAnimalsByTerritoryPaged(territoryId, pageSize, pageNumber * pageSize);
    }

    @GetMapping("/pageByTerritoryIdCount")
    public int getAnimalsByTerritoryIdPagesCount(
            @RequestParam int territoryId,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return animalRepository.getAnimalsByTerritoryPagesCount(territoryId, pageSize);
    }

    @GetMapping("/pageCount")
    public int getAnimalsPagesCount(@RequestParam(required = false, defaultValue = "10") int pageSize) {
        return animalRepository.getAnimalsPagesCount(pageSize);
    }

    @GetMapping("/checkRegulation")
    public boolean checkForRegulation() {
        return animalRepository.checkForRegulation();
    }

    @PostMapping("/updateAnimal")
    public void updateAnimal(@RequestParam long typeId, @RequestParam int territoryId, @RequestParam int change) {
        animalRepository.updateAnimal(typeId, territoryId, change);
    }
}
