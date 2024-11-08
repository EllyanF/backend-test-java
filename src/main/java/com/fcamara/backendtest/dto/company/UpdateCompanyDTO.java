package com.fcamara.backendtest.dto.company;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UpdateCompanyDTO(@NotBlank String name, @Valid AddressDTO address, String phone,
                               @Valid ParkingSpacesDTO parkingSpots) {
}
