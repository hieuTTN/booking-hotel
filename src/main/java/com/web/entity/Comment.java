package com.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float star;

    private String content;

    private Date createdDate;

    private Time createdTime;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private User user;
}
