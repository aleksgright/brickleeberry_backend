package com.example.brickleberry_backend.Controllers;

import com.example.brickleberry_backend.Entities.Report;
import com.example.brickleberry_backend.Repositories.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/report")
public class ReportsController {
    private final ReportRepository reportRepository;

    @GetMapping("/getByPersonId")
    public List<Report> getAllByPersonId(@RequestParam long personId){
        return reportRepository.getAllReportsByPersonId(personId);
    }
}
