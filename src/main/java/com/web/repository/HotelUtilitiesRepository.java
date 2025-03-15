package com.web.repository;

import com.web.entity.HotelUtilities;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HotelUtilitiesRepository extends JpaRepository<HotelUtilities, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete ru from hotel_utilities ru where ru.hotel_id = ?1", nativeQuery = true)
    int deleteByHotel(Long id);
}
