package com.medilabo.frontend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class PatientController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    public PatientController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<Map<String, Object>> patients = restTemplate.getForObject(gatewayUrl + "/api/patients", List.class);
        model.addAttribute("patients", patients);
        return "patients";
    }
}
