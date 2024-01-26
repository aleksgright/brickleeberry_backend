package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Dtos.InventarizationDataDto;
import com.example.brickleberry_backend.Repositories.FunctionRepository;
import com.example.brickleberry_backend.Repositories.ResourceRepository;
import com.example.brickleberry_backend.utils.InventarizationRequirement;
import com.example.brickleberry_backend.utils.RegulationRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/function")
public class FunctionController {
    private final FunctionRepository functionRepository;
    private final InventarizationRequirement inventarizationRequirement;
    private final RegulationRequirement regulationRequirement;
    private final ResourceRepository resourceRepository;


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

    @GetMapping("/checkRegulationRequirement")
    public boolean checkRegulationRequirement() {
        return regulationRequirement.isRegulationRequired();
    }

    @PostMapping("/updateRegulationRequirement")
    public void updateRegulationRequirement(
            @RequestParam(required = false, defaultValue = "false") boolean value) {
        regulationRequirement.setRegulationRequired(value);
    }

    @PostMapping("/inventarization")
    public void callInventarization(@RequestBody InventarizationDataDto inventarizationDataDto) {
        resourceRepository.getRfidChips(inventarizationDataDto.getChange(), inventarizationDataDto.getRfidId());
        functionRepository.callInventarization(inventarizationDataDto);
    }
}
