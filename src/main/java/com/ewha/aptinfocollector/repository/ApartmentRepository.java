package com.ewha.aptinfocollector.repository;

import com.ewha.aptinfocollector.VO.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public class ApartmentRepository extends JpaRepository<Apartment, Integer> {

}
