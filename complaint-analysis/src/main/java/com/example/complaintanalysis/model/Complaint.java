package com.example.complaintanalysis.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String category;
    private String status = "Pending";
    private LocalDateTime createdAt = LocalDateTime.now();
    private String location;
    private String reporterName;
    private String reporterContact;
}
