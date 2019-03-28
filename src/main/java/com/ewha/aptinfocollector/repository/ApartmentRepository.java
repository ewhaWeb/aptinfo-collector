package com.ewha.aptinfocollector.repository;

import com.ewha.aptinfocollector.VO.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    Apartment findApartmentByNameAndFloorAndSqm(String name, int floor, double sqm);
    boolean existsByNameAndSqmAndFloor(String name, double sqm, int floor);
}
