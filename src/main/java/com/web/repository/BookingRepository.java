package com.web.repository;

import com.web.entity.Booking;
import com.web.entity.BookingRoom;
import com.web.entity.User;
import com.web.enums.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("select b from Booking b where b.user.id = ?1")
    public List<Booking> myBooking(Long userId);

    @Query("select b from Booking b where b.createdDate >= ?1 and b.createdDate <= ?2")
    public List<Booking> allBooking(Date from, Date to);


    @Query(value = "select sum(b.amount_room + b.amount_service) from booking b where b.pay_status = ?3 and " +
            "MONTH(b.pay_date) = ?1 and Year(b.pay_date) = ?2", nativeQuery = true)
    Double calDt(Integer month, Integer year, PayStatus payStatus);
}
