package com.fcamara.backendtest.service;

import com.fcamara.backendtest.domain.ParkingSession;
import com.fcamara.backendtest.domain.Vehicle;
import com.fcamara.backendtest.domain.company.Company;
import com.fcamara.backendtest.domain.enums.SessionStatus;
import com.fcamara.backendtest.domain.enums.UpdateParkingLotOperators;
import com.fcamara.backendtest.domain.enums.VehicleType;
import com.fcamara.backendtest.dto.parkingLot.EnterVehicleDTO;
import com.fcamara.backendtest.dto.parkingLot.ExitVehicleDTO;
import com.fcamara.backendtest.exception.InvalidOperationException;
import com.fcamara.backendtest.repository.ParkingSessionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Service
public class ParkingLotService {
    private final ParkingSessionsRepository sessionsRepository;
    private final CompanyService companyService;
    private final VehicleService vehicleService;

    public ParkingLotService(
            ParkingSessionsRepository sessionsRepository,
            CompanyService companyService,
            VehicleService vehicleService
    ) {
        this.sessionsRepository = sessionsRepository;
        this.companyService = companyService;
        this.vehicleService = vehicleService;
    }

    @Transactional
    public ParkingSession enterParkingLot(Long companyId, EnterVehicleDTO data) {
        if (sessionsRepository.findActiveSession(companyId, data.vehicleId()).isPresent()) {
            throw new InvalidOperationException("Vehicle is already parked");
        }

        Company company = companyService.getById(companyId);
        Vehicle vehicle = vehicleService.getById(data.vehicleId());

        checkAvailability(company, vehicle.getType());

        ParkingSession session = new ParkingSession(
                vehicle,
                company,
                Objects.requireNonNullElse(data.entryDate(), LocalDateTime.now()),
                null
        );

        sessionsRepository.save(session);
        companyService.updateParkingLot(company, vehicle.getType(), UpdateParkingLotOperators.SUBTRACT);

        return session;
    }

    @Transactional
    public ParkingSession exitParkingLot(Long companyId, ExitVehicleDTO data) {
        ParkingSession session = sessionsRepository.findActiveSession(companyId, data.vehicleId()).orElseThrow();

        session.setExitTime(Objects.requireNonNullElse(data.exitDate(), LocalDateTime.now()));
        session.setStatus(SessionStatus.COMPLETED);

        companyService.updateParkingLot(
                companyService.getById(companyId),
                vehicleService.getById(data.vehicleId()).getType(),
                UpdateParkingLotOperators.ADD
        );

        return sessionsRepository.save(session);
    }

    private void checkAvailability(Company company, VehicleType type) {
        Map<VehicleType, Supplier<Integer>> spotAvailabilityMap = Map.of(
                VehicleType.CAR, company.getLotSpaces()::getCarParkingSpaces,
                VehicleType.MOTORCYCLE, company.getLotSpaces()::getMotorcycleParkingSpaces
        );

        Integer availableSpots = spotAvailabilityMap.getOrDefault(type, () -> 0).get();

        if (availableSpots == 0) throw new InvalidOperationException("There are no parking spaces available.");
    }
}
