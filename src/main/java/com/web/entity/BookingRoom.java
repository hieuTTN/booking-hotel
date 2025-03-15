package com.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "booking_room")
@Getter
@Setter
public class BookingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    private Date fromDate;

    private Date toDate;

    private Integer numDay;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Booking booking;
}
