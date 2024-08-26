package com.medilabo.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class PatientController {

    // Logger SLF4J
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Value("${gateway.url}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    public PatientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/patients")
    public ResponseEntity<?> getPatients() {
        logger.info("Request received to fetch patients data.");
        logger.debug("Gateway URL: {}", gatewayUrl);

        try {
            logger.info("Sending request to: {}/api/patients", gatewayUrl);
            List<Map<String, Object>> patients = restTemplate.getForObject(gatewayUrl + "/api/patients", List.class);
            logger.info("Received response with patients data: {}", patients);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            logger.error("Error occurred while fetching patients data from the gateway", e);
            return ResponseEntity.internalServerError().body("Error fetching patients data");
        }
    }
}