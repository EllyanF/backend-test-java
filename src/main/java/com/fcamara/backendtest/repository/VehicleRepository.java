package com.fcamara.backendtest.repository;

import com.fcamara.backendtest.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
