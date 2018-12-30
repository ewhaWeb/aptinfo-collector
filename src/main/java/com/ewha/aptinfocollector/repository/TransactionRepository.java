package com.ewha.aptinfocollector.repository;

import com.ewha.aptinfocollector.VO.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public class TransactionRepository extends JpaRepository<Apartment, Integer> {

}
