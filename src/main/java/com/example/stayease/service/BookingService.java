package com.example.stayease.service;

import com.example.stayease.entity.Booking;

public interface BookingService {
    public Booking bookRoom(Long userId,Long hotelId, Booking booking);

    public String cancelBooking(Long bookingId);
}
