package com.example.stayease.service.implementation;

import com.example.stayease.entity.Hotel;
import com.example.stayease.exceptions.ResourceAlreadyExistsException;
import com.example.stayease.exceptions.ResourceNotFoundException;
import com.example.stayease.repository.HotelRepository;
import com.example.stayease.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImp implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel createHotel(Hotel hotel) {

        Hotel existingHotel = hotelRepository.findByName(hotel.getName());

        if (hotelRepository.existsByName(hotel.getName()) && existingHotel.getLocation().equals(hotel.getLocation())){

            throw new ResourceAlreadyExistsException(HttpStatus.BAD_GATEWAY,"Hotel with same name and location already exists");

        }
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND , "Hotel with Id: "+id+" not found"));
    }

    @Override
    public Hotel updateHotel(Long id, Hotel hotelDetails) {

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND , "Hotel with Id: "+id+" not found"));

        hotel.setName(hotelDetails.getName());
        hotel.setLocation(hotelDetails.getLocation());
        hotel.setDescription(hotelDetails.getDescription());
        hotel.setAvailableRooms(hotelDetails.getAvailableRooms());
        return hotelRepository.save(hotel);
    }

    @Override
    public String deleteHotel(Long id) {

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND , "Hotel with Id: "+id+" not found"));

        hotelRepository.delete(hotel);

        return "Hotel with Id: "+id+" successfully deleted";
    }


    @Override
    public boolean isRoomAvailable(Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND , "Hotel with Id: "+hotelId+" not found"));

        return hotel.getAvailableRooms() > 0;
    }

    @Override
    public void decreaseAvailableRooms(Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND , "Hotel with Id: "+hotelId+" not found"));

        if (hotel.getAvailableRooms() > 0) {
            hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
            hotelRepository.save(hotel);
        } else {
            throw new RuntimeException("No available rooms");
        }
    }

    @Override
    public void increaseAvailableRooms(Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND , "Hotel with Id: "+hotelId+" not found"));

        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);
    }
}
