package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Dtos.ResourceAddDto;
import com.example.brickleberry_backend.Entities.Resource;
import com.example.brickleberry_backend.Repositories.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/resource")
public class ResourceController {
    private final ResourceRepository resourceRepository;

    @GetMapping("/getByWarehouseId")
    public List<Resource> getAllByWarehouseId(
            @RequestParam int warehouseId,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int pageNumber) {
        return resourceRepository.getResourcesByWarehouseIdPaged(warehouseId, pageSize, pageNumber * pageSize);
    }

    @GetMapping("/pageByWarehouseIdCount")
    public int getAnimalsByWarehouseIdPagesCount(
            @RequestParam int warehouseId,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return resourceRepository.getResourcesByWarehouseIdPagesCount(warehouseId, pageSize);
    }

    @PostMapping("/add")
    public boolean addResource(@RequestBody ResourceAddDto resourceAddDto) {
        return resourceRepository.addResource(resourceAddDto);
    }

    @PostMapping("/update")
    public boolean updateResource(@RequestParam long resourceId, @RequestParam int warehouseId, @RequestParam int change) {
        return resourceRepository.updateResource(resourceId, warehouseId, change);
    }
}
