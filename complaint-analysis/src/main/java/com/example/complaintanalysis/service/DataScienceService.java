package com.example.complaintanalysis.service;

import com.example.complaintanalysis.model.Complaint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class DataScienceService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Long> analyzeComplaintCategories(List<Complaint> complaints) {
        return callPythonAnalysis(complaints).get("categories");
    }

    public Map<String, Long> analyzeComplaintTrends(List<Complaint> complaints) {
        return callPythonAnalysis(complaints).get("trends");
    }

    private Map<String, Map<String, Long>> callPythonAnalysis(List<Complaint> complaints) {
        try {
            String jsonInput = objectMapper.writeValueAsString(complaints);
            
            ProcessBuilder pb = new ProcessBuilder(
                "python3", 
                System.getProperty("user.dir") + "/python/analysis.py"
            );
            Process p = pb.start();
            
            // Write input to Python process
            p.getOutputStream().write(jsonInput.getBytes());
            p.getOutputStream().close();
            
            // Read output from Python process
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            
            return objectMapper.readValue(output.toString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(
                "categories", Map.of("Error", -1L),
                "trends", Map.of("Error", -1L)
            );
        }
    }
}
