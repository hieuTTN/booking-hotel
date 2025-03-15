package com.web.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private Double price;

    private String description;

    private String image;

    private Integer numBed;

    private Integer maxPeople;

    @ManyToOne
    private Hotel hotel;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "room")
    private Set<RoomImage> roomImages;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "room")
    @JsonManagedReference
    private Set<RoomUtilities> roomUtilities;

}