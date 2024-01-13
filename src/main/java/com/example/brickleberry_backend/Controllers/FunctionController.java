package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Dtos.InventarizationDataDto;
import com.example.brickleberry_backend.Repositories.FunctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/function")
public class FunctionController {
    private final FunctionRepository functionRepository;

    @PostMapping("/regulation")
    public void callRegulation(@RequestParam int responsiblePersonId) {
        functionRepository.callRegulation(responsiblePersonId);
    }

    @PostMapping("/inventarization")
    public void callInventarization(@RequestBody InventarizationDataDto inventarizationDataDto) {
        functionRepository.callInventarization(inventarizationDataDto);
    }
}
