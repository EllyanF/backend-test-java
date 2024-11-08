package com.fcamara.backendtest.dto.parkingLot;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ExitVehicleDTO(@NotBlank Long vehicleId, @Nullable LocalDateTime exitDate) {
}
