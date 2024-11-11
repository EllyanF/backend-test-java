package com.fcamara.backendtest.controller;

import com.fcamara.backendtest.domain.Vehicle;
import com.fcamara.backendtest.dto.vehicle.UpdateVehicleDTO;
import com.fcamara.backendtest.dto.vehicle.VehicleDTO;
import com.fcamara.backendtest.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/vehicle", produces = {"application/json", "application/xml"})
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public Page<Vehicle> getVehicles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vehicleService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable Long id) {
        return vehicleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody @Valid VehicleDTO data) {
        return new ResponseEntity<>(vehicleService.create(data), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody UpdateVehicleDTO data) {
        return new ResponseEntity<>(vehicleService.updateById(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
