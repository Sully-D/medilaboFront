package com.medilabo.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
            // Créer les en-têtes HTTP et ajouter l'authentification Basic
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("user", "user");

            // Créer l'entité HTTP en utilisant les en-têtes
            HttpEntity<String> entity = new HttpEntity<>(headers);

            logger.info("Sending request to: {}/api/patients", gatewayUrl);
            ResponseEntity<List> response = restTemplate.exchange(gatewayUrl + "/api/patients",
                    HttpMethod.GET, entity, List.class);

            logger.info("Received response with patients data: {}", response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            logger.error("Error occurred while fetching patients data from the gateway", e);
            return ResponseEntity.internalServerError().body("Error fetching patients data");
        }
    }
}