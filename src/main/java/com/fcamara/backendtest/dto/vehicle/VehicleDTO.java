package com.fcamara.backendtest.dto.vehicle;

import com.fcamara.backendtest.domain.enums.VehicleType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public record VehicleDTO(@NotBlank String brand, @NotBlank String model, @NotBlank String color, @NotBlank String plate,
                         @Enumerated VehicleType type) {
}
