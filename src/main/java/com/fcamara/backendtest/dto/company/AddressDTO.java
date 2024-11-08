package com.fcamara.backendtest.dto.company;

import jakarta.validation.constraints.NotNull;

public record AddressDTO(@NotNull String address, @NotNull String city, @NotNull String cep, @NotNull String state) {
}
