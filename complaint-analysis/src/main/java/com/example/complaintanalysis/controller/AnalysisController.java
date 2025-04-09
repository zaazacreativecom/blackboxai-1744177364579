package com.example.complaintanalysis.controller;

import com.example.complaintanalysis.model.Complaint;
import com.example.complaintanalysis.repository.ComplaintRepository;
import com.example.complaintanalysis.service.DataScienceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {
    private final ComplaintRepository complaintRepository;
    private final DataScienceService dataScienceService;

    public AnalysisController(ComplaintRepository complaintRepository, 
                            DataScienceService dataScienceService) {
        this.complaintRepository = complaintRepository;
        this.dataScienceService = dataScienceService;
    }

    @GetMapping("/categories")
    public Map<String, Long> analyzeCategories() {
        return dataScienceService.analyzeComplaintCategories(complaintRepository.findAll());
    }

    @GetMapping("/trends")
    public Map<String, Long> analyzeTrends() {
        return dataScienceService.analyzeComplaintTrends(complaintRepository.findAll());
    }
}
