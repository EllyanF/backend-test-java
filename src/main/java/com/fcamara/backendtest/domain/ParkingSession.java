package com.fcamara.backendtest.domain;

import com.fcamara.backendtest.domain.company.Company;
import com.fcamara.backendtest.domain.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "ParkingSessions")
@Table(name = "parking_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ParkingSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @PrePersist
    protected void setDefaultStatus() {
        this.status = SessionStatus.ACTIVE;
    }

    public ParkingSession (Vehicle vehicle, Company company, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.vehicle = vehicle;
        this.company = company;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
