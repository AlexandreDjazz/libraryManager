package com.example.librarymanager.booking.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "booking")
@Access(AccessType.FIELD)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technical_id")
    private Long technicalId;

    @Embedded
    private BookingId id;

    protected Booking() {}

    private Booking(BookingId id) {
        this.id = id;
    }

    public Long getTechnicalId() {
        return technicalId;
    }

    public BookingId getId(){
        return this.id;
    }
}
