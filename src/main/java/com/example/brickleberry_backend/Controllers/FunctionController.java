package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Dtos.InventarizationDataDto;
import com.example.brickleberry_backend.Repositories.FunctionRepository;
import com.example.brickleberry_backend.utils.InventarizationRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/function")
public class FunctionController {
    private final FunctionRepository functionRepository;
    private final InventarizationRequirement inventarizationRequirement;

    @PostMapping("/regulation")
    public void callRegulation(@RequestParam int responsiblePersonId) {
        functionRepository.callRegulation(responsiblePersonId);
    }

    @GetMapping("/checkInventarizationRequirement")
    public boolean checkInventarizationRequirement() {
        return inventarizationRequirement.isInventarizationRequired();
    }

    @PostMapping("/updateInventarizationRequirement")
    public void updateInventarizationRequirement(
            @RequestParam(required = false, defaultValue = "false") boolean value) {
        inventarizationRequirement.setInventarizationRequired(value);
    }

    @PostMapping("/inventarization")
    public void callInventarization(@RequestBody InventarizationDataDto inventarizationDataDto) {
        functionRepository.callInventarization(inventarizationDataDto);
    }
}
