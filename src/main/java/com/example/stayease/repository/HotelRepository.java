package com.example.stayease.repository;

import com.example.stayease.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {

    boolean existsByName (String name);
    Hotel findByName (String name);
}
