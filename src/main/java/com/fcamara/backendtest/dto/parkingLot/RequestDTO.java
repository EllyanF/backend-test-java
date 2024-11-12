package com.fcamara.backendtest.dto.parkingLot;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record RequestDTO(@NotBlank Long companyId, @Nullable Long vehicleId) {
}
