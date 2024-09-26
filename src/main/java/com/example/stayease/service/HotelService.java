package com.example.stayease.service;

import com.example.stayease.entity.Hotel;

import java.util.List;

public interface HotelService {
    public List<Hotel> getAllHotels();

    public Hotel createHotel(Hotel hotel);

    public Hotel updateHotel(Long id, Hotel hotel);

    public String deleteHotel(Long id);

    public boolean isRoomAvailable(Long hotelId);

    public void decreaseAvailableRooms(Long hotelId);

    public void increaseAvailableRooms(Long hotelId);

    public Hotel getHotelById(Long id);
}