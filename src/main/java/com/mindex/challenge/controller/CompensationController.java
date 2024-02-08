package com.mindex.challenge.controller;

import com.mindex.challenge.config.SwaggerConfig;
import com.mindex.challenge.dto.model.CompensationDto;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {SwaggerConfig.EMPLOYEE_BOOK_TAG})
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    // Create a new compensation
    @PostMapping("/compensation")
    public ResponseEntity<?> createCompensation(@Valid @RequestBody CompensationDto compensationDto) {
        LOG.debug("Received request to create compensation [{}]", compensationDto);
        try {

            CompensationDto createdCompensation = compensationService.createCompensation(compensationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCompensation);

        } catch (Exception ex) {
            LOG.debug("Error creating compensation [{}]", compensationDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating compensation. " + ex.getMessage());
        }

    }

    // Get compensation by ID
    @GetMapping("/compensation/{id}")
    public ResponseEntity<?> getCompensationById(@PathVariable String id) {
        LOG.debug("Received request to retrieve compensation id {}.", id);
        try {

            CompensationDto compensation = compensationService.getCompensationByEmployeeId(id);
            return ResponseEntity.ok(compensation);

        } catch (Exception ex) {
            LOG.debug("Error retrieving compensation id {}.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Compensation id " + id + " not found. " + ex.getMessage());
        }

}

    // Update an existing compensation
    @PutMapping("/compensation/{id}")
    public ResponseEntity<?> updateCompensation(
            @PathVariable String id,
            @Valid @RequestBody CompensationDto compensationDto
    ) {
        LOG.debug("Received request to update compensation [{}].", compensationDto);
        try {

            CompensationDto updatedCompensation = compensationService.updateCompensation(compensationDto);
            return ResponseEntity.ok(updatedCompensation);

        } catch (Exception ex) {
            LOG.debug("Error updating compensation [{}].", compensationDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating compensation for employee id " + id + ". " + ex.getMessage());
        }

    }

}