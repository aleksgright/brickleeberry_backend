package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Entities.Person;
import com.example.brickleberry_backend.Repositories.PersonRepository;
import com.example.brickleberry_backend.Services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;
    private final PersonRepository personRepository;

    @GetMapping("/all")
    public List<Person> getPersons() {
        return personRepository.getAllPersons();
    }

    @GetMapping("/page")
    public List<Person> getPersonsPage(@RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam() int pageNumber) {
        return personRepository.getPersonsPage(pageSize, pageNumber * pageSize);
    }

    @PostMapping("/delete")
    public boolean deletePerson(@RequestParam int id) {
        return personRepository.deletePerson(id);
    }
}
