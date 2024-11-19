package com.fcamara.backendtest.domain;

import com.fcamara.backendtest.domain.enums.VehicleType;
import com.fcamara.backendtest.dto.vehicle.VehicleDTO;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    private String color;

    @Column(unique = true)
    private String plate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    public Vehicle(@NotNull VehicleDTO data) {
        this.brand = data.brand();
        this.model = data.model();
        this.color = data.color();
        this.plate = data.plate();
        this.type = data.type();
    }
}
