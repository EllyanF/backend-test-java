package com.fcamara.backendtest.controller;

import com.fcamara.backendtest.domain.ParkingSession;
import com.fcamara.backendtest.dto.parkingLot.HourlyReportDTO;
import com.fcamara.backendtest.dto.parkingLot.ReportDTO;
import com.fcamara.backendtest.dto.parkingLot.RequestDTO;
import com.fcamara.backendtest.service.ParkingLotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "api/parking-lots", produces = {"application/json", "application/xml"})
public class ParkingLotController {
    private final ParkingLotService service;

    public ParkingLotController (ParkingLotService service) {
        this.service = service;
    }

    @PostMapping("/enter")
    public ResponseEntity<ParkingSession> parkVehicle(@RequestBody RequestDTO data) {
        return new ResponseEntity<>(service.enterParkingLot(data), HttpStatus.CREATED);
    }

    @PutMapping("/exit")
    public ResponseEntity<ParkingSession> vehicleLeaves(@RequestBody RequestDTO data) {
        return new ResponseEntity<>(service.exitParkingLot(data), HttpStatus.OK);
    }

    @GetMapping
    public Page<ParkingSession> getSessions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllSessions(pageable);
    }

    @GetMapping("/reports/{companyId}")
    public ResponseEntity<ReportDTO> getSessionsReport(@PathVariable Long companyId) {
        return new ResponseEntity<>(service.getReport(companyId), HttpStatus.OK);
    }

    @GetMapping("/reports/{companyId}/hourly")
    public ResponseEntity<List<HourlyReportDTO>> getSessionsPerHour(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return new ResponseEntity<>(service.getSessionCountByHour(companyId, startDate, endDate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
