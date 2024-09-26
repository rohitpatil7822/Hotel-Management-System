package com.example.stayease.controller;

import com.example.stayease.entity.Booking;
import com.example.stayease.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private  BookingService bookingService;

    @PostMapping("/hotels/{userId}/{hotelId}/book")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Booking> bookRoom(@PathVariable Long userId,@PathVariable Long hotelId, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.bookRoom(userId ,hotelId, booking));
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {

        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }
}
