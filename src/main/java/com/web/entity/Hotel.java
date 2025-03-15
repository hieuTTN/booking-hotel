package com.web.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "hotel")
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String image;

    private String description;

    private String address;

    private Double avgPrice;

    private Integer numRating;

    private Float star;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<HotelImage> hotelImages;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<HotelUtilities> hotelUtilities;
}
