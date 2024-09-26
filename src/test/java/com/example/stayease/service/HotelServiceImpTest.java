package com.example.stayease.service;

import com.example.stayease.entity.Hotel;
import com.example.stayease.exceptions.ResourceAlreadyExistsException;
import com.example.stayease.exceptions.ResourceNotFoundException;
import com.example.stayease.repository.HotelRepository;
import com.example.stayease.service.implementation.HotelServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class HotelServiceImpTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImp hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setLocation("Test City");
        hotel.setDescription("A great hotel");
        hotel.setAvailableRooms(5);
    }

    @Test
    void testGetHotelById_HotelExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel foundHotel = hotelService.getHotelById(1L);

        assertNotNull(foundHotel);
        assertEquals(hotel.getId(), foundHotel.getId());
        assertEquals(hotel.getName(), foundHotel.getName());
    }

    @Test
    void testGetHotelById_HotelNotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            hotelService.getHotelById(1L);
        });

        assertEquals("Hotel with Id: 1 not found", exception.getMessage());
    }

    @Test
    void testCreateHotel_HotelAlreadyExists() {
        when(hotelRepository.existsByName(hotel.getName())).thenReturn(true);
        when(hotelRepository.findByName(hotel.getName())).thenReturn(hotel);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            hotelService.createHotel(hotel);
        });

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getHttpStatus());
        assertEquals("Hotel with same name and location already exists", exception.getMessage());
    }

    @Test
    void testCreateHotel_Success() {
        when(hotelRepository.existsByName(hotel.getName())).thenReturn(false);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Hotel savedHotel = hotelService.createHotel(hotel);

        assertNotNull(savedHotel);
        assertEquals(hotel.getName(), savedHotel.getName());
        assertEquals(hotel.getLocation(), savedHotel.getLocation());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void testDeleteHotel_HotelExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        String response = hotelService.deleteHotel(1L);

        assertEquals("Hotel with Id: 1 successfully deleted", response);
        verify(hotelRepository, times(1)).delete(hotel);
    }

    @Test
    void testDeleteHotel_HotelNotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            hotelService.deleteHotel(1L);
        });

        assertEquals("Hotel with Id: 1 not found", exception.getMessage());
    }
}
