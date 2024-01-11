package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Repositories.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/warehouse")
public class WarehouseController {
    private final WarehouseRepository warehouseRepository;

    @GetMapping("getByTerritoryId")
    public Map<String, Long> getAllByTerritoryId(@RequestParam int territoryId) {
        Map<String, Long> response = new HashMap<>();
        warehouseRepository.getWarehouseByTerritoryId(territoryId).forEach(w -> response.put(w.getName(), w.getResponsiblePersonId()));
        return response;
    }
}
