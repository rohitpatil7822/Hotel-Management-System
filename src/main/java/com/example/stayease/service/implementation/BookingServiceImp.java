package com.example.stayease.service.implementation;

import com.example.stayease.entity.Booking;
import com.example.stayease.entity.Hotel;
import com.example.stayease.entity.User;
import com.example.stayease.exceptions.ResourceNotFoundException;
import com.example.stayease.repository.BookingRepository;
import com.example.stayease.repository.HotelRepository;
import com.example.stayease.repository.UserRepository;
import com.example.stayease.service.BookingService;
import com.example.stayease.service.HotelService;
import com.example.stayease.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImp implements BookingService {


    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public Booking bookRoom(Long userId,Long hotelId, Booking booking) {

        Hotel hotel = hotelService.getHotelById(hotelId);
        User user = userService.getUserById(userId);

        if (!hotelService.isRoomAvailable(hotelId)){

            throw new IllegalStateException("Rooms are not available");
        }

        Booking booking1 = new Booking();
        booking1.setUser(user);
        booking1.setHotel(hotel);
        booking1.setCheckInDate(booking.getCheckInDate());
        booking1.setCheckOutDate(booking.getCheckOutDate());

        hotelService.decreaseAvailableRooms(hotelId);

        return  bookingRepository.save(booking1);
    }

    @Override
    @Transactional
    public String cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Booking with Id: "+bookingId+" not found"));

        hotelService.increaseAvailableRooms(booking.getHotel().getId());

        bookingRepository.delete(booking);

        return "Booking with Id: "+bookingId+" successfully deleted";
    }
}
