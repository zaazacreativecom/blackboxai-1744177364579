package com.example.complaintanalysis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
    @GetMapping("/")
    public String home() {
        return "complaints";
    }
    
    @GetMapping("/complaints")
    public String complaints() {
        return "complaints";
    }
}
