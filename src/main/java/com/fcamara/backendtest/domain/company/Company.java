package com.fcamara.backendtest.domain.company;

import com.fcamara.backendtest.dto.company.CompanyDTO;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity(name = "Companies")
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String cnpj;

    @Embedded
    private Address address;

    private String phone;

    @Embedded
    private ParkingLotSpaces lotSpaces;

    public Company(@NotNull CompanyDTO data) {
        this.name = data.name();
        this.cnpj = data.cnpj();
        this.address = new Address(data.address());
        this.phone = data.phone();
        this.lotSpaces = new ParkingLotSpaces(data.lotSpaces());
    }
}
