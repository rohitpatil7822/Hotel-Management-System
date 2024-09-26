package com.example.stayease.repository;

import com.example.stayease.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByUserId(Long userId);
    List<Booking> findByHotelId(Long hotelId);
}
