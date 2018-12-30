package com.ewha.aptinfocollector.repository;

import com.ewha.aptinfocollector.VO.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

}
