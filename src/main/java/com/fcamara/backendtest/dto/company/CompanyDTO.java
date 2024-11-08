package com.fcamara.backendtest.dto.company;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CompanyDTO(@NotNull String name, @NotNull String cnpj, @Valid AddressDTO address, @NotNull String phone,
                         @Valid ParkingSpacesDTO lotSpaces) {
}
