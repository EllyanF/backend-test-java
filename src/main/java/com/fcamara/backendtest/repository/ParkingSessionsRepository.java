package com.fcamara.backendtest.repository;

import com.fcamara.backendtest.domain.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSessionsRepository extends JpaRepository<ParkingSession, Long> {
    @Query("SELECT p FROM ParkingSessions p WHERE p.company.id = :companyId AND p.vehicle.id = :vehicleId and p.status = SessionStatus.ACTIVE ORDER BY p.entryTime ASC")
    Optional<ParkingSession> findActiveSession(@Param("companyId") Long companyId, @Param("vehicleId") Long vehicleId);
}
