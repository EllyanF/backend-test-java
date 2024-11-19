package com.fcamara.backendtest.repository;

import com.fcamara.backendtest.domain.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    @Query("SELECT p FROM ParkingSession p WHERE p.company.id = :companyId " +
            "AND p.vehicle.id = :vehicleId and p.status = SessionStatus.ACTIVE ORDER BY p.entryTime ASC")
    Optional<ParkingSession> findActiveSession(@Param("companyId") Long companyId, @Param("vehicleId") Long vehicleId);

    @Query("SELECT COUNT(ps.entryTime) AS entryCount, COUNT(ps.exitTime) AS exitCount " +
            "FROM ParkingSession ps WHERE ps.company.id = :id")
    List<Object[]> getSessionReport(@Param("id") Long id);

    @Query("SELECT DATE(ps.entryTime) AS date, HOUR(ps.entryTime) AS hour, " +
            "COUNT(ps.entryTime) AS entryCount, " +
            "COUNT(ps.exitTime) AS exitCount " +
            "FROM ParkingSession ps " +
            "WHERE ps.company.id = :companyId AND (ps.entryTime BETWEEN :startDate AND :endDate " +
            "OR ps.exitTime BETWEEN :startDate AND :endDate) " +
            "GROUP BY DATE(ps.entryTime), HOUR(ps.entryTime) " +
            "ORDER BY date ASC, hour ASC")
    List<Object[]> findEntriesAndExitsGroupedByHour(
            @Param("companyId") Long companyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
