package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Entities.Animal;
import com.example.brickleberry_backend.Repositories.AnimalRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/animal")
public class AnimalController {
    private final AnimalRepository animalRepository;

    @GetMapping("all")
    public List<Animal> getAllAnimalsPage(
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam() int pageNumber) {
        return animalRepository.getAllAnimalsPaged(pageSize, pageNumber * pageSize);
    }


    @GetMapping("animalsByTerritory")
    public List<Animal> getAnimalsByTerritoryPage(
            @RequestParam int territoryId,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam int pageNumber) {
        return animalRepository.getAnimalsByTerritoryPaged(territoryId, pageSize, pageNumber * pageSize);
    }
}
