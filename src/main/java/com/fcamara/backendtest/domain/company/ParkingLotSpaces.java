package com.fcamara.backendtest.domain.company;

import com.fcamara.backendtest.domain.enums.UpdateParkingLotOperators;
import com.fcamara.backendtest.domain.enums.VehicleType;
import com.fcamara.backendtest.dto.company.ParkingSpacesDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ParkingLotSpaces {
    @Column(name = "car_spaces")
    private Integer carParkingSpaces;

    @Column(name = "motorcycle_spaces")
    private Integer motorcycleParkingSpaces;

    public ParkingLotSpaces(@NotNull ParkingSpacesDTO data) {
        this.carParkingSpaces = data.carSpaces();
        this.motorcycleParkingSpaces = data.motorcycleSpaces();
    }

    public void updateLotSpaces(@NotNull VehicleType type, UpdateParkingLotOperators operator) {
        int adjustment = operator == UpdateParkingLotOperators.ADD ? 1 : -1;

        switch (type) {
            case CAR -> carParkingSpaces += adjustment;
            case MOTORCYCLE -> motorcycleParkingSpaces += adjustment;
        }
    }
}
