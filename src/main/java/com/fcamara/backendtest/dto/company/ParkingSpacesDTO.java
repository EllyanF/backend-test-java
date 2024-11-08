package com.fcamara.backendtest.dto.company;

import jakarta.validation.constraints.PositiveOrZero;

public record ParkingSpacesDTO(@PositiveOrZero Integer carSpaces, @PositiveOrZero Integer motorcycleSpaces) {
}
