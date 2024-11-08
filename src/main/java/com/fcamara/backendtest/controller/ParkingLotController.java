package com.fcamara.backendtest.controller;

import com.fcamara.backendtest.domain.ParkingSession;
import com.fcamara.backendtest.dto.parkingLot.EnterVehicleDTO;
import com.fcamara.backendtest.dto.parkingLot.ExitVehicleDTO;
import com.fcamara.backendtest.service.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/parking-lots")
public class ParkingLotController {
    private final ParkingLotService service;

    public ParkingLotController (ParkingLotService service) {
        this.service = service;
    }

    @PostMapping("/{companyId}/enter")
    public ResponseEntity<ParkingSession> parkVehicle(@PathVariable Long companyId, @RequestBody EnterVehicleDTO data) {
        return new ResponseEntity<>(service.enterParkingLot(companyId, data), HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}/exit")
    public ResponseEntity<ParkingSession> vehicleLeaves(@PathVariable Long companyId, @RequestBody ExitVehicleDTO data) {
        return new ResponseEntity<>(service.exitParkingLot(companyId, data), HttpStatus.OK);
    }
}
