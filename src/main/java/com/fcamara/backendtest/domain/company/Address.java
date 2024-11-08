package com.fcamara.backendtest.domain.company;

import com.fcamara.backendtest.dto.company.AddressDTO;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String address;
    private String city;
    private String cep;
    private String state;
    
    public Address(@NotNull AddressDTO data) {
        this.address = data.address();
        this.city = data.city();
        this.cep = data.cep();
        this.state = data.state();
    }
}
