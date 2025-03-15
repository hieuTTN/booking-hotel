package com.web.entity;

import com.web.enums.PayStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Date createdDate;

    private Date fromDate;

    private Date toDate;

    private Integer numDate;

    private Time createdTime;

    private Double amountRoom;

    private Double amountService;

    private String fullname;

    private String phone;

    private String cccd;

    private String note;

    private PayStatus payStatus;

    private Date payDate;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.REMOVE)
    private List<BookingRoom> bookingRooms;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
