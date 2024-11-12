package com.fcamara.backendtest.service;

import com.fcamara.backendtest.domain.ParkingSession;
import com.fcamara.backendtest.domain.Vehicle;
import com.fcamara.backendtest.domain.company.Company;
import com.fcamara.backendtest.domain.enums.SessionStatus;
import com.fcamara.backendtest.domain.enums.UpdateParkingLotOperators;
import com.fcamara.backendtest.domain.enums.VehicleType;
import com.fcamara.backendtest.dto.parkingLot.HourlyReportDTO;
import com.fcamara.backendtest.dto.parkingLot.ReportDTO;
import com.fcamara.backendtest.dto.parkingLot.RequestDTO;
import com.fcamara.backendtest.exception.InvalidOperationException;
import com.fcamara.backendtest.repository.ParkingSessionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {
    private final ParkingSessionRepository sessionRepository;
    private final CompanyService companyService;
    private final VehicleService vehicleService;

    public ParkingLotService(
            ParkingSessionRepository sessionsRepository,
            CompanyService companyService,
            VehicleService vehicleService
    ) {
        this.sessionRepository = sessionsRepository;
        this.companyService = companyService;
        this.vehicleService = vehicleService;
    }

    @Transactional
    public ParkingSession enterParkingLot(@NotNull RequestDTO data) {
        if (sessionRepository.findActiveSession(data.companyId(), data.vehicleId()).isPresent()) {
            throw new InvalidOperationException("Vehicle is already parked");
        }

        Company company = companyService.getById(data.companyId());
        Vehicle vehicle = vehicleService.getById(data.vehicleId());

        checkAvailability(company, vehicle.getType());

        ParkingSession session = new ParkingSession(vehicle, company);

        sessionRepository.save(session);
        companyService.updateParkingLot(company, vehicle.getType(), UpdateParkingLotOperators.SUBTRACT);

        return session;
    }

    @Transactional
    public ParkingSession exitParkingLot(@NotNull RequestDTO data) {
        ParkingSession session = sessionRepository.findActiveSession(data.companyId(), data.vehicleId()).orElseThrow();

        session.setExitTime(LocalDateTime.now());
        session.setStatus(SessionStatus.COMPLETED);

        companyService.updateParkingLot(
                companyService.getById(data.companyId()),
                vehicleService.getById(data.vehicleId()).getType(),
                UpdateParkingLotOperators.ADD
        );

        return sessionRepository.save(session);
    }

    public Page<ParkingSession> getAllSessions(Pageable pageable) {
        return sessionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ReportDTO getReport(Long id) {
        List<Object[]> results = sessionRepository.getSessionReport(id);
        Object[] row = results.get(0);

        return new ReportDTO((Long) row[0], (Long) row[1]);
    }

    @Transactional(readOnly = true)
    public List<HourlyReportDTO> getSessionCountByHour(Long companyId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = sessionRepository.findEntriesAndExitsGroupedByHour(companyId, startDate, endDate);

        return results.stream().map(
                row -> new HourlyReportDTO((Date) row[0], (Integer) row[1], (Long) row[2], (Long) row[3])
        ).collect(Collectors.toList());
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    private void checkAvailability(@NotNull Company company, VehicleType type) {
        Map<VehicleType, Supplier<Integer>> spotAvailabilityMap = Map.of(
                VehicleType.CAR, company.getLotSpaces()::getCarParkingSpaces,
                VehicleType.MOTORCYCLE, company.getLotSpaces()::getMotorcycleParkingSpaces
        );

        Integer availableSpots = spotAvailabilityMap.getOrDefault(type, () -> 0).get();

        if (availableSpots == 0) throw new InvalidOperationException("There are no parking spaces available.");
    }
}
